# Contributing to Swvl's Linta

First of all, we would like to welcome you into being a part of our community and are very much looking forward to all your contributions, small or big, whether it’s a typo, bug fix, or any feature.

## How Can I Contribute?

### Reporting Bugs

If you encounter any type of bugs, but you cannot afford the time to implement a fix for them, please open a new GitHub issue, and make sure to mention the details and the used Android Gradle Plugin version. Submitting a small sample in case of bugs would also be very useful and will help us reproduce the issue faster.

### Suggesting Enhancements

If you would like to introduce a huge change, or any new feature, or in case you discover any potential improvements, it's always better to discuss these changes in a GitHub issue first to make sure they are aligned with the library’s vision before moving ahead with the implementation, mentioning how that will improve the library or enhance the developer experience.

### Pull Requests

Pull requests are more than welcome whether it's for introducing any agreed upon features or improvements, fixing bugs or typos, or adding new documentation.

#### Guidelines

- Specify the type of the pull request.

    - **feat:** A new feature you're adding
    - **fix:** A bug fix
    - **style:** Updates related to styling
    - **refactor:** Refactoring a specific section of the codebase
    - **test:** Everything related to testing
    - **docs:** Everything related to documentation
    - **chore:** Regular code maintenance

- In the PR description, make sure to explain what changes you have made and why you made them.
- Keep in mind that the reviewer may not understand what the original problem was, so adding comments would often be helpful.
- Please follow the Git commit messages convention mentioned below as much as you can.

#### Testing

If a new feature or lint check is introduced, please make sure to write unit tests for it. When fixing bugs, we also recommend adding a test that highlights the broken behavior.

## Style Guides

### Git Commit Messages

- Separate the subject from the body with a blank line.
- Your commit message should not contain any whitespace errors.
- Do not end the subject line with a period.
- Capitalize the subject line and each paragraph.
- Use the present tense (e.g. "Add feature" not "Added feature").
- Use the imperative mood in the subject line (e.g. "Move cursor to..." not "Moves cursor to...").

### Kotlin Style Guide
Please follow the official [Kotlin coding conventions](https://kotlinlang.org/docs/reference/coding-conventions.html) when contributing.
