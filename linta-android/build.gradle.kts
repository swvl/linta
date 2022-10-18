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

plugins {
    id("java-library")
    id("com.android.lint")
    id("org.jetbrains.kotlin.jvm")
}

java {
    sourceCompatibility = JavaVersion.VERSION_11
    targetCompatibility = JavaVersion.VERSION_11
}

dependencies {
    compileOnly("com.android.tools.lint:lint-api:30.3.1")

    testImplementation("junit:junit:4.13.2")
    testImplementation("com.android.tools.lint:lint:30.3.1")
    testImplementation("com.android.tools.lint:lint-tests:30.3.1")
}

tasks.withType<Jar> {
    manifest {
        attributes["Lint-Registry-v2"] = "com.swvl.lint.LintIssueRegistry"
    }
}
