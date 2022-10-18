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

import com.android.tools.lint.checks.infrastructure.LintDetectorTest.xml
import com.android.tools.lint.checks.infrastructure.TestLintTask
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@Suppress("UnstableApiUsage")
@RunWith(JUnit4::class)
class DuplicateColorsDetectorTest {

    @Test
    fun `Given a duplicate color in the same file, When we analyze our custom rule, Then display a warning`() {
        TestLintTask.lint()
            .files(
                xml(
                    "res/values/colors.xml",
                    """
                    <resources>
                        <color name="white1">#FFFFFF</color>
                        <color name="white2">#FFFFFF</color>
                    </resources>
                    """
                ).indented()
            )
            .issues(DuplicateColorsDetector.ISSUE)
            .allowMissingSdk()
            .run()
            .expect(
                """
            |res/values/colors.xml:2: Warning: Duplicate color value #FFFFFF at white1 and white2 [DuplicateColors]
            |    <color name="white1">#FFFFFF</color>
            |    ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
            |    res/values/colors.xml:3: Duplicates value in white1
            |0 errors, 1 warnings""".trimMargin()
            )
    }

    @Test
    fun `Given a duplicate color in another file, When we analyze our custom rule, Then display a warning`() {
        TestLintTask.lint()
            .files(
                xml(
                    "res/values/colors1.xml",
                    """
                    <resources>
                        <color name="white1">#FFFFFF</color>
                    </resources>
                    """
                ).indented(),
                xml(
                    "res/values/colors2.xml",
                    """
                    <resources>
                        <color name="white2">#FFFFFF</color>
                    </resources>
                    """
                ).indented()
            )
            .issues(DuplicateColorsDetector.ISSUE)
            .allowMissingSdk()
            .run()
            .expect(
                """
            |res/values/colors1.xml:2: Warning: Duplicate color value #FFFFFF at white1 and white2 [DuplicateColors]
            |    <color name="white1">#FFFFFF</color>
            |    ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
            |    res/values/colors2.xml:2: Duplicates value in white1
            |0 errors, 1 warnings""".trimMargin()
            )
    }

    @Test
    fun `Given duplicate colors with different casing, When we analyze our custom rule, Then display a warning`() {
        TestLintTask.lint()
            .files(
                xml(
                    "res/values/colors.xml",
                    """
                    <resources>
                        <color name="color1">#ABCD12</color>
                        <color name="color2">#abcd12</color>
                        <color name="color3">#ABcd12</color>
                    </resources>
                    """
                ).indented()
            )
            .issues(DuplicateColorsDetector.ISSUE)
            .allowMissingSdk()
            .run()
            .expect(
                """
            |res/values/colors.xml:2: Warning: Duplicate color value #ABCD12 at color1, color2 and color3 [DuplicateColors]
            |    <color name="color1">#ABCD12</color>
            |    ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
            |    res/values/colors.xml:3: Duplicates value in color1
            |    res/values/colors.xml:4: Duplicates value in color1
            |0 errors, 1 warnings""".trimMargin()
            )
    }

    @Test
    fun `Given duplicate colors with different length, When we analyze our custom rule, Then display a warning`() {
        TestLintTask.lint()
            .files(
                xml(
                    "res/values/colors.xml",
                    """
                    <resources>
                        <color name="black1">#FF000000</color>
                        <color name="black2">#000000</color>
                        <color name="black3">#F000</color>
                        <color name="black4">#000</color>
                    </resources>
                    """
                ).indented()
            )
            .issues(DuplicateColorsDetector.ISSUE)
            .allowMissingSdk()
            .run()
            .expect(
                """
            |res/values/colors.xml:2: Warning: Duplicate color value #000000 at black1, black2, black3 and black4 [DuplicateColors]
            |    <color name="black1">#FF000000</color>
            |    ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
            |    res/values/colors.xml:3: Duplicates value in black1
            |    res/values/colors.xml:4: Duplicates value in black1
            |    res/values/colors.xml:5: Duplicates value in black1
            |0 errors, 1 warnings""".trimMargin()
            )
    }

    @Test
    fun `Given no duplicate colors, When we analyze our custom rule, Then expect no warnings`() {
        TestLintTask.lint()
            .files(
                xml(
                    "res/values/colors.xml",
                    """
                    <resources>
                        <color name="white">#FFFFFF</color>
                        <color name="black">#000000</color>
                    </resources>
                    """
                ).indented()
            )
            .issues(DuplicateColorsDetector.ISSUE)
            .allowMissingSdk()
            .run()
            .expectClean()
    }
}
