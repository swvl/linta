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
import com.android.tools.lint.checks.infrastructure.TestLintTask.lint
import com.android.tools.lint.detector.api.Severity
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@Suppress("UnstableApiUsage")
@RunWith(JUnit4::class)
class RedundantStylesDetectorTest {

    @Test
    fun `Given a style with no attributes, then display a warning`() {
        lint()
            .files(
                xml(
                    "res/values/styles.xml",
                    """
                    <resources>
                        <style name="Theme.App" parent="Theme.MaterialComponents.Light.NoActionBar" />
                    </resources>
                    """
                ).indented()
            )
            .issues(RedundantStylesDetector.ISSUE)
            .allowMissingSdk()
            .run()
            .expectCount(1, Severity.WARNING)
    }

    @Test
    fun `Given a style with one attribute, then expect no warnings`() {
        lint()
            .files(
                xml(
                    "res/values-v23/another-styles-file.xml",
                    """
                    <resources>
                        <style name="Theme.App" parent="Theme.MaterialComponents.Light.NoActionBar">
                            <item name="colorPrimary">@color/primary</item>
                        </style>
                    </resources>
                    """
                ).indented()
            )
            .issues(RedundantStylesDetector.ISSUE)
            .allowMissingSdk()
            .run()
            .expectClean()
    }
}
