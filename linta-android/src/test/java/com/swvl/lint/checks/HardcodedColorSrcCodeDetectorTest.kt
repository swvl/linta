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

import com.android.tools.lint.checks.infrastructure.LintDetectorTest.java
import com.android.tools.lint.checks.infrastructure.LintDetectorTest.kotlin
import com.android.tools.lint.checks.infrastructure.TestLintTask.lint
import com.android.tools.lint.detector.api.Severity
import com.swvl.lint.Stubs
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@Suppress("UnstableApiUsage")
@RunWith(JUnit4::class)
class HardcodedColorSrcCodeDetectorTest {

    @Test
    fun `Given a hardcoded color, When parseColor is used, Then display an error`() {
        lint()
            .files(
                Stubs.ANDROID_COLOR,
                java(
                    """
                    package foo;
                    import android.graphics.Color;
                    public class Example { 
                        public int getColor() {
                            return Color.parseColor("#FFFFFF");
                        }
                    }
                    """
                ).indented(),
                kotlin(
                    """
                    package foo
                    import android.graphics.Color
                    class Example {
                        fun getColor(): Int {
                            return Color.parseColor("#FFFFFF") 
                        }
                    }
                    """
                ).indented()
            )
            .issues(HardcodedColorSrcCodeDetector.ISSUE)
            .allowMissingSdk()
            .run()
            .expectCount(2, Severity.ERROR)
    }

    @Test
    fun `Given a hardcoded color, When valueOf is used, Then display an error`() {
        lint()
            .files(
                Stubs.ANDROID_COLOR,
                java(
                    """
                    package foo;
                    import android.graphics.Color;
                    public class Example { 
                        public void createColors() {
                            Color colorInt = Color.valueOf(0x00FFFFFF);
                            Color colorLong = Color.valueOf(0xFFFFFFFF);
                            Color colorRgb = Color.valueOf(1f, 1f, 1f);
                            Color colorArgb = Color.valueOf(1f, 1f, 1f, 1f);
                        }
                    }
                    """
                ).indented(),
                kotlin(
                    """
                    package foo
                    import android.graphics.Color
                    class Example {
                        fun createColors() {
                            val colorInt = Color.valueOf(0x00FFFFFF)
                            val colorLong = Color.valueOf(0xFFFFFFFF)
                            val colorRgb = Color.valueOf(1f, 1f, 1f)
                            val colorArgb = Color.valueOf(1f, 1f, 1f, 1f) 
                        }
                    }
                    """
                ).indented()
            )
            .issues(HardcodedColorSrcCodeDetector.ISSUE)
            .allowMissingSdk()
            .run()
            .expectCount(8, Severity.ERROR)
    }

    @Test
    fun `Given a hardcoded color, When rgb is used, Then display an error`() {
        lint()
            .files(
                Stubs.ANDROID_COLOR,
                java(
                    """
                    package foo;
                    import android.graphics.Color;
                    public class Example { 
                        public void createColors() {
                            int colorInt = Color.rgb(255, 255, 255);
                            int colorFloat = Color.rgb(1f, 1f, 1f);
                        }
                    }
                    """
                ).indented(),
                kotlin(
                    """
                    package foo
                    import android.graphics.Color
                    class Example {
                        fun createColors() {
                            val colorInt = Color.rgb(255, 255, 255)
                            val colorFloat = Color.rgb(1f, 1f, 1f) 
                        }
                    }
                    """
                ).indented()
            )
            .issues(HardcodedColorSrcCodeDetector.ISSUE)
            .allowMissingSdk()
            .run()
            .expectCount(4, Severity.ERROR)
    }

    @Test
    fun `Given a hardcoded color, When argb is used, Then display an error`() {
        lint()
            .files(
                Stubs.ANDROID_COLOR,
                java(
                    """
                    package foo;
                    import android.graphics.Color;
                    public class Example { 
                        public void createColors() {
                            int colorInt = Color.argb(255, 255, 255, 255);
                            int colorFloat = Color.argb(1f, 1f, 1f, 1f);
                        }
                    }
                    """
                ).indented(),
                kotlin(
                    """
                    package foo
                    import android.graphics.Color
                    class Example {
                        fun createColors() {
                            val colorInt = Color.argb(255, 255, 255, 255)
                            val colorFloat = Color.argb(1f, 1f, 1f, 1f) 
                        }
                    }
                    """
                ).indented()
            )
            .issues(HardcodedColorSrcCodeDetector.ISSUE)
            .allowMissingSdk()
            .run()
            .expectCount(4, Severity.ERROR)
    }

    @Test
    fun `Given no hardcoded color, When parseColor is used, Then expect no error`() {
        lint()
            .files(
                Stubs.ANDROID_COLOR,
                java(
                    """
                    package foo;
                    import android.graphics.Color;
                    public class Example { 
                        public int getColor(String color) {
                            return Color.parseColor(color);
                        }
                    }
                    """
                ).indented(),
                kotlin(
                    """
                    package foo
                    import android.graphics.Color
                    class Example { 
                        fun getColor(color: String): Int {
                            return Color.parseColor(color) 
                        }
                    }
                    """
                ).indented()
            )
            .issues(HardcodedColorSrcCodeDetector.ISSUE)
            .allowMissingSdk()
            .run()
            .expectClean()
    }

    @Test
    fun `Given no hardcoded color, When valueOf is used, Then expect no error`() {
        lint()
            .files(
                Stubs.ANDROID_COLOR,
                java(
                    """
                    package foo;
                    import android.graphics.Color;
                    public class Example { 
                        public Color createColor(int color) {
                            return Color.valueOf(color);
                        }
                        public Color createColor(long color) {
                            return Color.valueOf(color);
                        }
                        public Color createColor(float r, float g, float b) {
                            return Color.valueOf(r, g, b);
                        }
                        public Color createColor(float r, float g, float b, float a) {
                            return Color.valueOf(r, g, b, a);
                        }
                    }
                    """
                ).indented(),
                kotlin(
                    """
                    package foo
                    import android.graphics.Color
                    class Example {
                        fun createColor(color: Int): Color {
                            return Color.valueOf(color) 
                        }
                        fun createColor(color: Long): Color { 
                            return Color.valueOf(color) 
                        }
                        fun createColor(r: Float, g: Float, b: Float): Color {
                            return Color.valueOf(r, g, b) 
                        }
                        fun createColor(r: Float, g: Float, b: Float, a: Float): Color {
                            return Color.valueOf(r, g, b, a) 
                        }
                    }
                    """
                ).indented()
            )
            .issues(HardcodedColorSrcCodeDetector.ISSUE)
            .allowMissingSdk()
            .run()
            .expectClean()
    }

    @Test
    fun `Given no hardcoded color, When rgb is used, Then expect no error`() {
        lint()
            .files(
                Stubs.ANDROID_COLOR,
                java(
                    """
                    package foo;
                    import android.graphics.Color;
                    public class Example { 
                        public int createColor(int r, int g, int b) {
                            return Color.rgb(r, g, b);
                        }
                        public int createColor(float r, float g, float b) {
                            return Color.rgb(r, g, b);
                        }
                    }
                    """
                ).indented(),
                kotlin(
                    """
                    package foo
                    import android.graphics.Color
                    class Example {
                        fun createColor(r: Int, g: Int, b: Int): Int {
                            return Color.rgb(r, g, b) 
                        }
                        fun createColor(r: Float, g: Float, b: Float): Int { 
                            return Color.rgb(r, g, b) 
                        }
                    }
                    """
                ).indented()
            )
            .issues(HardcodedColorSrcCodeDetector.ISSUE)
            .allowMissingSdk()
            .run()
            .expectClean()
    }

    @Test
    fun `Given no hardcoded color, When argb is used, Then expect no error`() {
        lint()
            .files(
                Stubs.ANDROID_COLOR,
                java(
                    """
                    package foo;
                    import android.graphics.Color;
                    public class Example { 
                        public int createColor(int a, int r, int g, int b) {
                            return Color.argb(a, r, g, b);
                        }
                        public int createColor(float a, float r, float g, float b) {
                            return Color.argb(a, r, g, b);
                        }
                    }
                    """
                ).indented(),
                kotlin(
                    """
                    package foo
                    import android.graphics.Color
                    class Example {
                        fun createColor(a: Int, r: Int, g: Int, b: Int): Int { 
                            return Color.argb(a, r, g, b) 
                        }
                        fun createColor(a: Float, r: Float, g: Float, b: Float): Int { 
                            return Color.argb(a, r, g, b) 
                        }
                    }
                    """
                ).indented()
            )
            .issues(HardcodedColorSrcCodeDetector.ISSUE)
            .allowMissingSdk()
            .run()
            .expectClean()
    }
}
