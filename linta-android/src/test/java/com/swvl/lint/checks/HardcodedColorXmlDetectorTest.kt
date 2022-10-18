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
class HardcodedColorXmlDetectorTest {

    @Test
    fun `Given a hardcoded color on a custom text view property, When we analyze our custom rule, Then display an error`() {
        lint()
            .files(
                xml(
                    "res/layout/layout.xml",
                    """
                    <TextView xmlns:app="http://schemas.android.com/apk/res-auto"
                        app:someCustomColor="#fff" />
                    """
                ).indented()
            )
            .issues(HardcodedColorXmlDetector.ISSUE)
            .allowMissingSdk()
            .run()
            .expectCount(1, Severity.ERROR)
    }

    @Test
    fun `Given a hardcoded color on a text view, When we analyze our custom rule, Then display an error`() {
        lint()
            .files(
                xml(
                    "res/layout/layout.xml",
                    """
                        <TextView xmlns:android="http://schemas.android.com/apk/res/android"
                            android:textColor="#80000000" />
                        """
                ).indented()
            )
            .issues(HardcodedColorXmlDetector.ISSUE)
            .allowMissingSdk()
            .run()
            .expectCount(1, Severity.ERROR)
    }

    @Test
    fun `Given a color from our resources on a text view, When we analyze our custom rule, Then expect no errors`() {
        lint()
            .files(
                xml(
                    "res/layout/layout.xml",
                    """
                        <TextView xmlns:android="http://schemas.android.com/apk/res/android"
                            android:textColor="@color/primaryColor" />
                        """
                ).indented()
            )
            .issues(HardcodedColorXmlDetector.ISSUE)
            .allowMissingSdk()
            .run()
            .expectClean()
    }
}
