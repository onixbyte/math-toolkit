# Contributing

We appreciate your interest in contributing to our open-source projects. Contributions are welcome
in various forms such as code, documentation, bug reports, and test cases. To ensure a smooth
collaboration, please follow the guidelines outlined below.

## Getting Started

Our libraries are several open-source Java libraries designed to ease Java enterprise application
development. They are built with Amazon Corretto 17.

## Development Setup

There is no need for manual setup beyond ensuring you have Amazon Corretto 17 and a stable internet
connection. Our projects use Gradle Wrapper for building, simplifying the setup process.

```shell
./gradlew build
```

## Branching Strategy

We follow the `git-flow` branching model. Contributors should fork this repository and create their
work on separate branches prefixed with `feature/` or `hotfix/` as appropriate.

## Code Style

Please adhere to the coding standards specified in our `.editorconfig` file in the root of each
repositories. Consistent style helps in maintaining readability and uniformity across the codebase.

## Commit Messages

We require that commit messages follow this structure:

```text
type[(scope)]: subject

[body]

[BREAKING CHANGE: breaking changes]
```

This format helps in auto-generating changelogs and understanding the purpose behind changes.

## Submitting Contributions

1. Fork the repository.
2. Create a branch: `git checkout -b feature/my-new-feature` or `git checkout -b hotfix/my-fix`.
3. Make your changes and commit them.
4. Push to the branch: `git push origin feature/my-new-feature`.
5. Create a Pull Request with a succinct subject and a detailed body.

## Testing

We encourage using JUnit Jupiter for unit and integration tests. Pull Requests with accompanying
test reports are prioritised for review and merging.

## License

By contributing to our open source projects, you agree that your contributions will be licensed
under the MIT license. If you do not agree to this, please refrain from contributing.