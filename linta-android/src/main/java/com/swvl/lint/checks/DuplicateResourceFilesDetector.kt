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
import org.w3c.dom.Node
import java.io.StringWriter
import javax.xml.transform.TransformerFactory
import javax.xml.transform.dom.DOMSource
import javax.xml.transform.stream.StreamResult

@Suppress("UnstableApiUsage")
class DuplicateResourceFilesDetector : ResourceXmlDetector() {

    data class ResourceDeclaration(val name: String, val locationHandle: Location.Handle)

    private val resources = HashMap<String, MutableList<ResourceDeclaration>>()

    private lateinit var currentDocument: String

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
        val stringWriter = StringWriter()

        // The transformer will auto-order the elements attributes.
        TransformerFactory.newInstance().newTransformer()
            .transform(DOMSource(document), StreamResult(stringWriter))

        currentDocument = stringWriter.toString()
        stringWriter.flush()

        removeToolsNamespaceAttributes(document.firstChild ?: return)

        // Remove whitespaces.
        currentDocument = currentDocument.replace("\\s+".toRegex(), "")

        // Cache the document.
        resources[currentDocument] =
            resources.getOrDefault(currentDocument, ArrayList()).apply {
                add(ResourceDeclaration(context.file.name, context.createLocationHandle(document)))
            }
    }

    private fun removeToolsNamespaceAttributes(node: Node?) {
        // Remove all attributes under from the tools namespace.
        val attributesCount = node?.attributes?.length ?: 0
        for (i in 0 until attributesCount) {
            val attr = node?.attributes?.item(i)
            if (attr?.namespaceURI == TOOLS_NAMESPACE_URI) {
                currentDocument = currentDocument.replace(
                    attr.toString(),
                    ""
                )
            }
        }

        // Do the same with all children.
        val childrenCount = node?.childNodes?.length ?: 0
        for (i in 0 until childrenCount) {
            val child = node?.childNodes?.item(i)
            removeToolsNamespaceAttributes(child)
        }
    }

    override fun afterCheckRootProject(context: Context) {
        // Clear the last document.
        currentDocument = ""

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
