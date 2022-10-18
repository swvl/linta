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

import org.junit.Assert.*

import org.junit.Test

class ColorUtilsTest {

    @Test
    fun isColor() {
        // Upper case color
        assertTrue(ColorUtils.isColor("#ABCDEF"))

        // Lower case color
        assertTrue(ColorUtils.isColor("#abcdef"))

        // Mixed case color
        assertTrue(ColorUtils.isColor("#ABcd00"))

        // Color length (3, 4, 6, 8 valid)
        assertTrue(ColorUtils.isColor("#123"))
        assertTrue(ColorUtils.isColor("#1234"))
        assertTrue(ColorUtils.isColor("#123456"))
        assertTrue(ColorUtils.isColor("#12345678"))

        // Color length (1, 2, 5, 7 invalid)
        assertFalse(ColorUtils.isColor("#1"))
        assertFalse(ColorUtils.isColor("#12"))
        assertFalse(ColorUtils.isColor("#12345"))
        assertFalse(ColorUtils.isColor("#1234567"))
    }

    @Test
    fun get8DigitColorFormat() {
        assertEquals("#AB123456", ColorUtils.get8DigitColorFormat("#AB123456"))
        assertEquals("#FF123456", ColorUtils.get8DigitColorFormat("#123456"))
        assertEquals("#AA112233", ColorUtils.get8DigitColorFormat("#A123"))
        assertEquals("#FF112233", ColorUtils.get8DigitColorFormat("#123"))
    }

    @Test
    fun getBestColorFormat() {
        assertEquals("#12345678", ColorUtils.getBestColorFormat("#12345678"))
        assertEquals("#123456", ColorUtils.getBestColorFormat("#FF123456"))
        assertEquals("#123456", ColorUtils.getBestColorFormat("#123456"))
        assertEquals("#112233", ColorUtils.getBestColorFormat("#F123"))
        assertEquals("#112233", ColorUtils.getBestColorFormat("#123"))
    }
}
