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

package com.swvl.lint.utils

import java.util.*

object ColorUtils {

    /**
     * Hex colors can be represented in 3, 4, 6, or 8 digits.
     */
    private val REGEX_HEX_COLOR =
        "#([a-fA-F\\d]{3}|[a-fA-F\\d]{4}|[a-fA-F\\d]{6}|[a-fA-F\\d]{8})".toRegex()

    private const val MESSAGE_INVALID_COLOR =
        "Colors should be hexadecimal and represented in 3, 4, 6, or 8 digits."

    /**
     * @return true if the text represents a color in hexadecimal format.
     */
    fun isColor(text: String): Boolean {
        return text.matches(REGEX_HEX_COLOR)
    }

    /**
     * @return the 8-digit hexadecimal format of a color of any length.
     */
    fun get8DigitColorFormat(hexColor: String): String {
        val newColor = hexColor.toUpperCase(Locale.US)

        return when (newColor.length) {
            9 -> {
                // Return the same string in upper case if it's already an 8-digit color.
                newColor
            }
            7 -> {
                // Add the alpha component.
                buildString(capacity = 9) {
                    append("#FF")
                    append(newColor.substring(1))
                }
            }
            5 -> {
                // Expand each digit to 2 digits.
                buildString(capacity = 9) {
                    append("#")
                    newColor.substring(1).forEach { digit ->
                        repeat(2) { append(digit) }
                    }
                }
            }
            4 -> {
                // Add the alpha component, and expand each digit to 2 digits.
                buildString(capacity = 9) {
                    append("#FF")
                    newColor.substring(1).forEach { digit ->
                        repeat(2) { append(digit) }
                    }
                }
            }
            else -> {
                throw IllegalArgumentException(MESSAGE_INVALID_COLOR)
            }
        }
    }

    /**
     * @return the 6-digit hexadecimal format of a color of any length, or the 8-digit hexadecimal
     * format if an alpha component exists.
     */
    fun getBestColorFormat(hexColor: String): String {
        val newColor = hexColor.toUpperCase(Locale.US)

        return when (newColor.length) {
            9 -> {
                if (newColor.substring(1, 3) == "FF") {
                    // Return the 6-digit hexadecimal format.
                    buildString(capacity = 7) {
                        append("#")
                        append(newColor.substring(3))
                    }
                } else {
                    newColor
                }
            }
            7 -> {
                newColor
            }
            5, 4 -> {
                getBestColorFormat(get8DigitColorFormat(newColor))
            }
            else -> {
                throw IllegalArgumentException(MESSAGE_INVALID_COLOR)
            }
        }
    }
}
