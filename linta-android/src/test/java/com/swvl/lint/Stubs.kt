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

package com.swvl.lint

import com.android.tools.lint.checks.infrastructure.LintDetectorTest.kotlin
import com.android.tools.lint.checks.infrastructure.TestFile

@Suppress("UnstableApiUsage")
object Stubs {

    val ANDROID_COLOR: TestFile = kotlin(
        """
        package android.graphics
        
        object Color {
        
            fun parseColor(colorString: String): Int {
                // Stub!
            }
        
            fun valueOf(color: Int): Color { 
                // Stub!
            }
        
            fun valueOf(color: Long): Color { 
                // Stub!
            }
        
            fun valueOf(r: Float, g: Float, b: Float): Color { 
                // Stub!
            }
        
            fun valueOf(r: Float, g: Float, b: Float, a: Float): Color { 
                // Stub!
            }
        
            fun rgb(r: Int, g: Int, b: Int): Int { 
                // Stub!
            }
        
            fun rgb(r: Float, g: Float, b: Float): Int { 
                // Stub!
            }
        
            fun argb(a: Int, r: Int, g: Int, b: Int): Int { 
                // Stub!
            }
        
            fun argb(a: Float, r: Float, g: Float, b: Float): Int { 
                // Stub!
            }
        }
        """
    ).indented()
}
