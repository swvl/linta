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

import com.android.SdkConstants
import com.android.resources.ResourceFolderType
import com.android.tools.lint.detector.api.*
import org.w3c.dom.Element

@Suppress("UnstableApiUsage")
class RedundantStylesDetector : ResourceXmlDetector() {

    /**
     * The lint check is only applicable for resources located inside the "values" folder(s).
     */
    override fun appliesTo(folderType: ResourceFolderType): Boolean {
        return folderType == ResourceFolderType.VALUES
    }

    /**
     * If the element is a style resource, then call [visitElement].
     */
    override fun getApplicableElements(): Collection<String> {
        return listOf(SdkConstants.TAG_STYLE)
    }

    override fun visitElement(context: XmlContext, element: Element) {
        val childNodes = element.childNodes
        if (childNodes.length == 0) {
            context.report(
                Incident(
                    issue = ISSUE,
                    scope = element,
                    location = context.getElementLocation(element),
                    message = ISSUE.getExplanation(TextFormat.TEXT)
                )
            )
        }
    }

    companion object {
        val ISSUE = Issue.create(
            id = "RedundantStyles",
            briefDescription = "A defined style with no distinctive attributes",
            explanation = "Empty styles that don't define their own attributes are mostly redundant and should generally be avoided.",
            category = Category.CORRECTNESS,
            severity = Severity.WARNING,
            implementation = Implementation(
                RedundantStylesDetector::class.java,
                Scope.RESOURCE_FILE_SCOPE
            )
        )
    }
}
