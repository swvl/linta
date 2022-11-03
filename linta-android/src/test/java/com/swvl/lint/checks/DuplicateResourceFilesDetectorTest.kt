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
import com.android.tools.lint.detector.api.Severity
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@Suppress("UnstableApiUsage")
@RunWith(JUnit4::class)
class DuplicateResourceFilesDetectorTest {

    @Test
    fun `Given duplicate shape resources with different white-spacing, a warning should be reported`() {
        TestLintTask.lint()
            .files(
                xml(
                    "res/drawable/shape1.xml",
                    """
                    <shape xmlns:android="http://schemas.android.com/apk/res/android"
                        android:shape="rectangle">

                        <solid android:color="@color/white" />

                        <stroke
                            android:width="1dp"
                            android:color="@color/black_12" />

                        <corners android:radius="24dp" />

                    </shape>
                    """
                ).indented(),
                xml(
                    "res/drawable/shape2.xml",
                    """
                     <shape xmlns:android="http://schemas.android.com/apk/res/android"
                         android:shape="rectangle">
                            <solid android:color="@color/white" />
                            <stroke
                                android:width="1dp"
                                android:color="@color/black_12" />
                            <corners android:radius="24dp" />
                    </shape>
                    """
                ).indented()
            )
            .issues(DuplicateResourceFilesDetector.ISSUE)
            .allowMissingSdk()
            .run()
            .expectCount(1, Severity.WARNING)
    }

    @Test
    fun `Given different shape resources, no warning should be reported`() {
        TestLintTask.lint()
            .files(
                xml(
                    "res/drawable/shape1.xml",
                    """
                    <shape xmlns:android="http://schemas.android.com/apk/res/android"
                        android:shape="rectangle">
                        <solid android:color="@color/white" />
                        <stroke
                            android:width="1dp"
                            android:color="@color/black" />
                        <corners android:radius="24dp" />
                    </shape>
                    """
                ).indented(),
                xml(
                    "res/drawable/shape2.xml",
                    """
                    <shape xmlns:android="http://schemas.android.com/apk/res/android"
                        android:shape="rectangle">
                        <solid android:color="@color/white" />
                        <stroke
                            android:width="1dp"
                            android:color="@color/black_12" />
                        <corners android:radius="24dp" />
                    </shape>
                    """
                ).indented()
            )
            .issues(DuplicateResourceFilesDetector.ISSUE)
            .allowMissingSdk()
            .run()
            .expectClean()
    }

    @Test
    fun `Given 2 duplicate resources, one with attributes that are under the tools namespace and another without, a warning should be reported`() {
        TestLintTask.lint()
            .files(
                xml(
                    "res/layout/item_title.xml",
                    """
                    <TextView xmlns:android="http://schemas.android.com/apk/res/android"
                        xmlns:tools="http://schemas.android.com/tools"
                        android:id="@+id/tv_id"
                        android:layout_width="match_parent"
                        android:layout_height="wrap_content"
                        android:background="?selectableItemBackground"
                        android:paddingHorizontal="32dp"
                        android:paddingVertical="16dp"
                        android:textColor="#000000"
                        android:textSize="20sp"
                        tools:text="Title" />
                    """
                ).indented(),
                xml(
                    "res/layout/item_option.xml",
                    """
                    <TextView xmlns:android="http://schemas.android.com/apk/res/android"
                        android:id="@+id/tv_id"
                        android:layout_width="match_parent"
                        android:layout_height="wrap_content"
                        android:background="?selectableItemBackground"
                        android:paddingHorizontal="32dp"
                        android:paddingVertical="16dp"
                        android:textColor="#000000"
                        android:textSize="20sp" />
                    """
                ).indented()
            )
            .issues(DuplicateResourceFilesDetector.ISSUE)
            .allowMissingSdk()
            .run()
            .expectCount(1, Severity.WARNING)
    }

    @Test
    fun `Given 2 duplicate resources with different attributes that are under the tools namespace, a warning should be reported`() {
        TestLintTask.lint()
            .files(
                xml(
                    "res/layout/layout1.xml",
                    """
                    <merge xmlns:android="http://schemas.android.com/apk/res/android"
                        xmlns:app="http://schemas.android.com/apk/res-auto"
                        xmlns:tools="http://schemas.android.com/tools"
                        android:layout_width="match_parent"
                        android:layout_height="wrap_content"
                        tools:parentTag="androidx.constraintlayout.widget.ConstraintLayout">
                        <TextView
                            android:id="@+id/around_tv"
                            style="@style/Font.PublicSansBold"
                            android:layout_width="0dp"
                            android:layout_height="wrap_content"
                            android:layout_marginBottom="4dp"
                            android:text="@string/searchResults_tripCard_around_label_title"
                            android:textColor="@color/black"
                            android:textSize="10sp"
                            app:layout_constraintBottom_toTopOf="@id/time_text"
                            app:layout_constraintEnd_toEndOf="@id/time_text"
                            app:layout_constraintStart_toStartOf="@id/time_text"
                            app:layout_constraintVertical_chainStyle="packed"
                            tools:ignore="smallSp"
                            tools:text="different text"
                            tools:visibility="visible" />
                    </merge>
                    """
                ).indented(),
                xml(
                    "res/layout/layout2.xml",
                    """
                    <merge xmlns:android="http://schemas.android.com/apk/res/android"
                        xmlns:app="http://schemas.android.com/apk/res-auto"
                        xmlns:tools="http://schemas.android.com/tools"
                        android:layout_width="match_parent"
                        android:layout_height="wrap_content"
                        tools:parentTag="androidx.constraintlayout.widget.ConstraintLayout">
                        <TextView
                            android:id="@+id/around_tv"
                            style="@style/Font.PublicSansBold"
                            android:layout_width="0dp"
                            android:layout_height="wrap_content"
                            android:layout_marginBottom="4dp"
                            android:text="@string/searchResults_tripCard_around_label_title"
                            android:textColor="@color/black"
                            android:textSize="10sp"
                            app:layout_constraintBottom_toTopOf="@id/time_text"
                            app:layout_constraintEnd_toEndOf="@id/time_text"
                            app:layout_constraintStart_toStartOf="@id/time_text"
                            app:layout_constraintVertical_chainStyle="packed"
                            tools:ignore="smallSp"
                            tools:text="Around"
                            tools:visibility="gone" />
                     </merge>
                     """
                ).indented()
            )
            .issues(DuplicateResourceFilesDetector.ISSUE)
            .allowMissingSdk()
            .run()
            .expectCount(1, Severity.WARNING)
    }

    @Test
    fun `Given duplicate resources with different attributes order, a warning should be reported`() {
        TestLintTask.lint()
            .files(
                xml(
                    "res/drawable/resource1.xml",
                    """
                    <inset xmlns:android="http://schemas.android.com/apk/res/android"
                        android:insetTop="-1dp">
                        <shape android:shape="rectangle">
                            <padding
                                android:bottom="16dp"
                                android:top="16dp" />
                            <solid android:color="@color/white" />
                            <stroke
                                android:width="1dp"
                                android:color="@color/black_10" />
                        </shape>
                    </inset>
                    """
                ).indented(),
                xml(
                    "res/drawable/resource2.xml",
                    """
                    <inset xmlns:android="http://schemas.android.com/apk/res/android"
                        android:insetTop="-1dp">
                        <shape android:shape="rectangle">
                            <padding
                                android:top="16dp" 
                                android:bottom="16dp" />
                            <solid android:color="@color/white" />
                            <stroke
                                android:color="@color/black_10"
                                android:width="1dp" />
                        </shape>
                    </inset>
                    """
                ).indented()
            )
            .issues(DuplicateResourceFilesDetector.ISSUE)
            .allowMissingSdk()
            .run()
            .expectCount(1, Severity.WARNING)
    }

    @Test
    fun `Given duplicate shape resources, where one doesn't have the xml declaration, a warning should be reported`() {
        TestLintTask.lint()
            .files(
                xml(
                    "res/drawable/shape1.xml",
                    """
                    <?xml version="1.0" encoding="utf-8"?>
                    <shape xmlns:android="http://schemas.android.com/apk/res/android"
                        android:shape="rectangle">
                        <solid android:color="@color/white" />
                        <stroke
                            android:width="1dp"
                            android:color="@color/black_12" />
                        <corners android:radius="24dp" />
                    </shape>
                    """
                ).indented(),
                xml(
                    "res/drawable/shape2.xml",
                    """
                    <shape xmlns:android="http://schemas.android.com/apk/res/android"
                        android:shape="rectangle">
                        <solid android:color="@color/white" />
                        <stroke
                            android:width="1dp"
                            android:color="@color/black_12" />
                        <corners android:radius="24dp" />
                    </shape>
                    """
                ).indented()
            )
            .issues(DuplicateResourceFilesDetector.ISSUE)
            .allowMissingSdk()
            .run()
            .expectCount(1, Severity.WARNING)
    }

    @Test
    fun `Given a resource with hardcoded colors but with the lint issue suppressed, when the duplicate resource files detector is run before the hardcoded color detector, no issue should be reported`() {
        TestLintTask.lint()
            .files(
                xml(
                    "res/drawable/shape.xml",
                    """
                    <shape xmlns:android="http://schemas.android.com/apk/res/android"
                        xmlns:tools="http://schemas.android.com/tools"
                        android:shape="rectangle"
                        tools:ignore="HardcodedColorXml">
                        <solid android:color="#FFFFFF" />
                        <stroke
                            android:width="1dp"
                            android:color="#000000" />
                        <corners android:radius="24dp" />
                    </shape>
                    """
                ).indented()
            )
            .issues(DuplicateResourceFilesDetector.ISSUE, HardcodedColorXmlDetector.ISSUE)
            .allowMissingSdk()
            .run()
            .expectClean()
    }
}
