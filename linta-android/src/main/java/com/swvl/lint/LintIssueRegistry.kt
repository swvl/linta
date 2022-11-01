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

import com.android.tools.lint.client.api.IssueRegistry
import com.android.tools.lint.client.api.Vendor
import com.android.tools.lint.detector.api.CURRENT_API
import com.android.tools.lint.detector.api.Issue
import com.swvl.lint.checks.*

@Suppress("UnstableApiUsage", "unused")
class LintIssueRegistry : IssueRegistry() {

    override val issues: List<Issue>
        get() = listOf(
            HardcodedColorXmlDetector.ISSUE,
            HardcodedColorSrcCodeDetector.ISSUE,
            DuplicateColorsDetector.ISSUE,
            DuplicateResourceFilesDetector.ISSUE,
            RedundantStylesDetector.ISSUE
        )

    override val api: Int
        get() = CURRENT_API

    /**
     * Lint checks work with Android Studio 4.0 or later.
     *
     * @see [com.android.tools.lint.detector.api.describeApi]
     */
    override val minApi: Int
        get() = 7

    /**
     * The vendor is used to determine the source of the reported lint issues in lint reports.
     */
    override val vendor = Vendor(
        vendorName = "Swvl",
        identifier = "linta-android",
        feedbackUrl = "https://github.com/swvl/linta"
    )
}
