/**
 * Copyright 2022 Swvl
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.swvl.lint.checks

import com.android.resources.ResourceFolderType
import com.android.tools.lint.detector.api.*
import org.w3c.dom.Document
import org.w3c.dom.Element
import org.w3c.dom.Node
import java.io.StringWriter
import javax.xml.transform.TransformerFactory
import javax.xml.transform.dom.DOMSource
import javax.xml.transform.stream.StreamResult

@Suppress("UnstableApiUsage")
class DuplicateResourceFilesDetector : ResourceXmlDetector() {

    data class ResourceDeclaration(val name: String, val locationHandle: Location.Handle)

    private val resources = HashMap<String, MutableList<ResourceDeclaration>>()

    override fun appliesTo(folderType: ResourceFolderType): Boolean {
        return folderType in setOf(
            ResourceFolderType.LAYOUT,
            ResourceFolderType.DRAWABLE,
            ResourceFolderType.ANIM,
            ResourceFolderType.INTERPOLATOR,
            ResourceFolderType.MENU,
            ResourceFolderType.MIPMAP,
            ResourceFolderType.TRANSITION,
            ResourceFolderType.COLOR,
            ResourceFolderType.XML
        )
    }

    override fun visitDocument(context: XmlContext, document: Document) {
        // Deep clone the document to not affect the original document, as the same document
        // instance is used in all other lint detectors.
        val documentClone = document.cloneNode(true)

        removeToolsNamespaceAttributesAndComments(documentClone.firstChild ?: return)

        val stringWriter = StringWriter()

        // The transformer will auto-order the elements attributes.
        TransformerFactory.newInstance().newTransformer()
            .transform(DOMSource(documentClone), StreamResult(stringWriter))

        // Remove whitespaces.
        val currentDocument = stringWriter.buffer.replace("\\s+".toRegex(), "")

        stringWriter.flush()

        // Cache the document.
        resources[currentDocument] =
            resources.getOrDefault(currentDocument, ArrayList()).apply {
                add(ResourceDeclaration(context.file.name, context.createLocationHandle(document)))
            }
    }

    private fun removeToolsNamespaceAttributesAndComments(node: Node) {
        if (node.nodeType == Element.ELEMENT_NODE) {
            // Remove tools namespace and all attributes under it.
            var i = 0
            while (i < node.attributes.length) {
                val attr = node.attributes.item(i)
                if (attr.namespaceURI == TOOLS_NAMESPACE_URI || attr.nodeValue == TOOLS_NAMESPACE_URI) {
                    node.attributes.removeNamedItem(attr.nodeName)
                    continue
                }
                i++
            }
        } else if (node.nodeType == Element.COMMENT_NODE) {
            // Remove comment nodes.
            node.parentNode.removeChild(node)
        }

        // Do the same with all children.
        var i = 0
        while (i < node.childNodes.length) {
            val child = node.childNodes.item(i)
            removeToolsNamespaceAttributesAndComments(child)

            // Only increase counter if node isn't a comment
            if (child.nodeType == Element.COMMENT_NODE) {
                continue
            }
            i++
        }
    }

    override fun afterCheckRootProject(context: Context) {
        for ((_, resource) in resources) {
            if (resource.size > 1) {
                val firstLocation = resource[0].locationHandle.resolve()

                context.report(
                    Incident(
                        issue = ISSUE,
                        scope = firstLocation.source,
                        location = firstLocation,
                        message = "Duplicate resources at ${
                            formatList(
                                resource.map { "`${it.name}`" },
                                useConjunction = true
                            )
                        }"
                    )
                )
            }
        }
    }

    companion object {

        private const val TOOLS_NAMESPACE_URI = "http://schemas.android.com/tools"

        val ISSUE = Issue.create(
            id = "DuplicateResourceFiles",
            briefDescription = "Duplicate XML resources under different names",
            explanation = "Having multiple identical resources files with different names makes the code harder to maintain and increases the app's size unnecessarily.",
            category = Category.CORRECTNESS,
            severity = Severity.WARNING,
            implementation = Implementation(
                DuplicateResourceFilesDetector::class.java,
                Scope.ALL_RESOURCES_SCOPE
            )
        )
    }
}
