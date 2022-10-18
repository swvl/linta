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
import com.swvl.lint.utils.ColorUtils
import org.w3c.dom.Element

@Suppress("UnstableApiUsage")
class DuplicateColorsDetector : ResourceXmlDetector() {

    /**
     * Used to hold the location handle of a color resource.
     */
    data class ColorDeclaration(val name: String, val locationHandle: Location.Handle)

    /**
     * Map of all colors in upper case to their declarations, to ensure that there are no duplicate
     * colors.
     */
    private val allColors = HashMap<String, MutableList<ColorDeclaration>>()

    /**
     * The lint check is only applicable for resources located inside the "values" folder(s).
     */
    override fun appliesTo(folderType: ResourceFolderType): Boolean {
        return folderType == ResourceFolderType.VALUES
    }

    /**
     * If the element is a color resource, then call [visitElement].
     */
    override fun getApplicableElements(): Collection<String> {
        return listOf(SdkConstants.TAG_COLOR)
    }

    /**
     * When a color resource is visited, add the color resource to [allColors] if it is a valid
     * color.
     */
    override fun visitElement(context: XmlContext, element: Element) {
        // Read the color resource value.
        val colorValue = element.firstChild.nodeValue.trim()

        if (!ColorUtils.isColor(colorValue)) return

        val color8Digit = ColorUtils.get8DigitColorFormat(colorValue)

        // Add the color declaration to the color duplicates list.
        val colorDuplicates = allColors.getOrDefault(color8Digit, ArrayList())
        colorDuplicates.add(
            ColorDeclaration(
                name = element.getAttribute(SdkConstants.ATTR_NAME),
                locationHandle = context.createLocationHandle(element)
            )
        )

        allColors[color8Digit] = colorDuplicates
    }

    /**
     * Report the duplicate colors after visiting all color elements.
     */
    override fun afterCheckRootProject(context: Context) {
        for ((color, declarations) in allColors) {
            if (declarations.size > 1) {
                val firstLocation: Location = declarations[0].locationHandle.resolve()
                var prevLocation: Location = firstLocation
                val namesList = mutableListOf(declarations[0].name)

                // Relate all duplicates to the first declaration.
                for (duplicate in declarations.subList(1, declarations.size)) {
                    namesList.add(duplicate.name)

                    val location = duplicate.locationHandle.resolve()
                    location.message = "Duplicates value in `${namesList[0]}`"
                    location.setSelfExplanatory(false)

                    prevLocation.secondary = location
                    prevLocation = location
                }

                val colorBestFormat = ColorUtils.getBestColorFormat(color)

                val names = formatList(namesList.map { "`$it`" }, useConjunction = true)
                val message = "Duplicate color value `$colorBestFormat` at $names"

                context.report(
                    Incident(
                        issue = ISSUE,
                        scope = firstLocation.source,
                        location = firstLocation,
                        message = message
                    )
                )
            }
        }
    }

    companion object {
        val ISSUE = Issue.create(
            id = "DuplicateColors",
            briefDescription = "Duplicate colors in color resources",
            explanation = "Duplicate colors can lead to app theme inconsistencies if a color gets updated without updating the other.",
            category = Category.CORRECTNESS,
            severity = Severity.WARNING,
            implementation = Implementation(
                DuplicateColorsDetector::class.java,
                Scope.ALL_RESOURCES_SCOPE,
                Scope.RESOURCE_FILE_SCOPE
            )
        )
    }
}
