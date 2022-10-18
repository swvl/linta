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

import com.android.tools.lint.detector.api.*
import com.android.tools.lint.detector.api.Detector.UastScanner
import com.intellij.psi.PsiMethod
import org.jetbrains.uast.UCallExpression

@Suppress("UnstableApiUsage")
class HardcodedColorSrcCodeDetector : Detector(), UastScanner {

    /**
     * If the method name is any of the following, then call [visitMethodCall].
     */
    override fun getApplicableMethodNames() =
        listOf(METHOD_PARSE_COLOR, METHOD_VALUE_OF, METHOD_RGB, METHOD_ARGB)

    /**
     * Apply all the lint checks defined here if the [method] is one of [getApplicableMethodNames].
     */
    override fun visitMethodCall(context: JavaContext, node: UCallExpression, method: PsiMethod) {
        val evaluator = context.evaluator

        // Check if `android.graphics.Color` is used instead of the color resources.
        if (evaluator.isMemberInClass(method, "android.graphics.Color")) {
            when (method.name) {
                METHOD_PARSE_COLOR -> {
                    val colorArg = node.valueArguments.first()
                    if (ConstantEvaluator.evaluateString(context, colorArg, false) == null) {
                        // The color value cannot be evaluated.
                        return
                    }
                }
                METHOD_VALUE_OF, METHOD_RGB, METHOD_ARGB -> {
                    node.valueArguments.forEach { colorArg ->
                        if (ConstantEvaluator.evaluate(context, colorArg) == null) {
                            // The color value cannot be evaluated.
                            return
                        }
                    }
                }
            }

            context.report(
                Incident(
                    issue = ISSUE,
                    scope = node,
                    location = context.getLocation(node),
                    message = "Hardcoded colors should be declared in a color resource."
                )
            )
        }
    }

    companion object {
        private const val METHOD_PARSE_COLOR = "parseColor"
        private const val METHOD_VALUE_OF = "valueOf"
        private const val METHOD_RGB = "rgb"
        private const val METHOD_ARGB = "argb"

        val ISSUE = Issue.create(
            id = "HardcodedColorSrcCode",
            briefDescription = "Hardcoded color in source code",
            explanation = "Hardcoded colors should be declared as a color resource.",
            category = Category.CORRECTNESS,
            severity = Severity.ERROR,
            implementation = Implementation(
                HardcodedColorSrcCodeDetector::class.java,
                Scope.JAVA_FILE_SCOPE
            )
        )
    }
}
