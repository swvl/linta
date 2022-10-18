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

import com.android.tools.lint.detector.api.*
import com.swvl.lint.utils.ColorUtils
import org.w3c.dom.Attr

@Suppress("UnstableApiUsage")
class HardcodedColorXmlDetector : ResourceXmlDetector() {

    override fun getApplicableAttributes(): Collection<String>? {
        // Return the set of attribute names we want to analyze. The `visitAttribute` method
        // below will be called each time lint sees one of these attributes in a
        // XML resource file. In this case, we want to analyze every attribute
        // in every XML resource file.
        return XmlScannerConstants.ALL
    }

    override fun visitAttribute(context: XmlContext, attribute: Attr) {
        // Get the value of the XML attribute.
        val attributeValue = attribute.nodeValue
        if (ColorUtils.isColor(attributeValue)) {
            context.report(
                issue = ISSUE,
                scope = attribute,
                location = context.getValueLocation(attribute),
                message = "Hardcoded colors should be declared in a color resource."
            )
        }
    }

    companion object {
        val ISSUE = Issue.create(
            id = "HardcodedColorXml",
            briefDescription = "Hardcoded color in XML file",
            explanation = "Hardcoded colors should be declared as a color resource.",
            category = Category.CORRECTNESS,
            severity = Severity.ERROR,
            implementation = Implementation(
                HardcodedColorXmlDetector::class.java,
                Scope.RESOURCE_FILE_SCOPE
            )
        )
    }
}
