# Changelog

All notable changes to this project will be documented in this file.

The format is based on [Keep a Changelog](https://keepachangelog.com/en/1.0.0/), and this project adheres
to [Semantic Versioning](https://semver.org/spec/v2.0.0.html).

## [Unreleased]

### Added

- Support generating accessors for generic types ([#10](https://github.com/nesk/akkurate/issues/10))

## [0.2.0] - 2023-09-26

### ⚠️ Breaking changes

- Change visibility of `ValidateAnnotationProcessor.validatableOfFunction` to private. ([#12](https://github.com/nesk/akkurate/issues/12))
- Change visibility of `ValidateAnnotationProcessor.validatableClass` to private. ([#12](https://github.com/nesk/akkurate/issues/12))

### Added

- Add new constraints ([#3](https://github.com/nesk/akkurate/issues/3)):
  - `CharSequence`
    - `hasLengthEqualTo` / `hasLengthNotEqualTo`  
    - `isBlank` / `isNotBlank`  
    - `isMatching` / `isNotMatching`
  - `kotlin.collections`
    - `hasSizeNotEqualTo`
    - `isEmpty` / `isNotEmpty`
    - `isContaining` / `isNotContaining`
  - `Map`
    - `isContainingKey` / `isNotContainingKey`
    - `isContainingValue` / `isNotContainingValue`

### Changed

- Enable [Explicit API Mode](https://kotlinlang.org/docs/jvm-api-guidelines-backward-compatibility.html#explicit-api-mode) for the KSP plugin. ([#12](https://github.com/nesk/akkurate/issues/12)) 

## [0.1.1] - 2023-09-21

### Fixed

- The target version of generated JVM bytecode is now 1.8 instead of 11. ([#5](https://github.com/nesk/akkurate/issues/5))
- Suppress the warning about useless casts in generated accessors. ([#8](https://github.com/nesk/akkurate/issues/8))

## [0.1.0] - 2023-09-12

First release

[Unreleased]: https://github.com/nesk/akkurate/compare/0.2.0...HEAD
[0.2.0]: https://github.com/nesk/akkurate/compare/0.1.1...0.2.0
[0.1.1]: https://github.com/nesk/akkurate/compare/0.1.0...0.1.1
[0.1.0]: https://github.com/nesk/akkurate/releases/tag/0.1.0
