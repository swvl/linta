# Linta

A set of useful lint checks developed with Android Lint API to keep the app follow a solid design system

## Motivation

Large-scale mobile apps often involve large teams of developers. Without proper lint checks in a large project's codebase, one might fall into the pitfall of inconsistent UI designs with different colors being used in layouts and other resources, making the app theme hard to maintain.

For more details, check [Lint Checks for a Solid Design System](https://docs.google.com/presentation/d/1aDEtm2UE2OoRO59Pt8fb4S43iM79BFncj0pR57WVEvw) session presented at Droidcon Egypt 2022.

## Usage

To use Linta in your project, add the following line to `build.gradle` file.

[![Maven Central](https://img.shields.io/maven-central/v/com.swvl.lint/linta-android.svg)](https://mvnrepository.com/artifact/com.swvl.lint/linta-android)

```groovy
lintChecks "com.swvl.lint:linta-android:x.y.z"
```

## License

```
Copyright 2022 Swvl

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

    http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
```
