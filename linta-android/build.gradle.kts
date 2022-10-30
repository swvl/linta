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

import com.vanniktech.maven.publish.SonatypeHost

plugins {
    id("java-library")
    id("com.android.lint")
    id("org.jetbrains.kotlin.jvm")
    id("com.vanniktech.maven.publish") version "0.22.0"
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

mavenPublishing {
    publishToMavenCentral(SonatypeHost.S01)
    signAllPublications()

    pom {
        name.set("Linta (Android)")
        description.set("A set of useful lint checks to make the app follow a solid design system.")
        inceptionYear.set("2022")
        url.set("https://github.com/swvl/linta")

        licenses {
            license {
                name.set("The Apache License, Version 2.0")
                url.set("https://www.apache.org/licenses/LICENSE-2.0.txt")
                distribution.set("https://www.apache.org/licenses/LICENSE-2.0.txt")
            }
        }

        developers {
            developer {
                name.set("Swvl Android OSS")
                email.set("android-open-source@swvl.com")
                organization.set("Swvl")
                organizationUrl.set("https://www.swvl.com")
            }
        }

        scm {
            url.set("https://github.com/swvl/linta")
            connection.set("scm:git:git://github.com/swvl/linta.git")
            developerConnection.set("scm:git:ssh://git@github.com/swvl/linta.git")
        }
    }
}
