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

package com.swvl.lintsample

import android.graphics.Color

class HardcodedColorSrcCodeSample {

    fun issueExamples() {
        // Hardcoded parameters
        Color.parseColor("#FFFFFF")

        Color.valueOf(0x00FFFFFF)
        Color.valueOf(0xFFFFFFFF)
        Color.valueOf(1f, 1f, 1f)
        Color.valueOf(1f, 1f, 1f, 1f)

        Color.rgb(255, 255, 255)
        Color.rgb(1f, 1f, 1f)

        Color.argb(255, 255, 255, 255)
        Color.argb(1f, 1f, 1f, 1f)

        // Referencing a constant
        Color.parseColor(COLOR_WHITE)
    }

    fun cleanExample(color: String) {
        // Unknown color
        Color.parseColor(color)
    }

    companion object {
        private const val COLOR_WHITE = "#FFFFFF"
    }
}
