
# Changelog

All notable changes to this project will be documented in this file.

The format is based on [Keep a Changelog](https://keepachangelog.com/en/1.0.0/), and this project adheres
to [Semantic Versioning](https://semver.org/spec/v2.0.0.html).

## [Unreleased]

### Added

- New `akkurate-test` artifact to test custom constraints.
- New constraints to validate types of the [kotlin.time](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.time/) package.

### Fixed

- Add a `or equal to zero` suffix to the default message for `isNegativeOrZero` and `isPositiveOrZero` constraints.

## [0.9.1] - 2024-09-12

### Fixed

- Fix name clashes when generating validatable accessors from the common code.

## [0.9.0] - 2024-08-30

### ⚠️ Breaking changes

- [The `ConstraintViolationSet.equals` method](https://akkurate.dev/api/akkurate-core/dev.nesk.akkurate.constraints/-constraint-violation-set/equals.html) is now symmetric and matches [what's in the specification.](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-any/equals.html)

### Added

- Support Kotlin Multiplatform and all its targets ([#33](https://github.com/nesk/akkurate/issues/33))

### Changed

- The default violation message of the [`isInstanceOf`](https://akkurate.dev/api/akkurate-core/dev.nesk.akkurate.constraints.builders/is-instance-of.html) and [`isNotInstanceOf`](https://akkurate.dev/api/akkurate-core/dev.nesk.akkurate.constraints.builders/is-not-instance-of.html) constraints now display the simple name of the type, instead of the qualified one. 
- [The `Validate` annotation](https://akkurate.dev/api/akkurate-core/dev.nesk.akkurate.annotations/-validate/index.html) has been moved from the `akkurate-ksp-plugin` artifact to the `akkurate-core` one. 

## [0.8.0]

### Added

- Support transforming a value before validating it ([#26](https://github.com/nesk/akkurate/issues/26))
- New constraint to ensure a collection doesn't contain duplicated elements ([#28](https://github.com/nesk/akkurate/issues/28))
- New constraints to check if a value is an instance of some type ([#27](https://github.com/nesk/akkurate/issues/27))
- New accessors to easily validate a specific index of a collection ([#29](https://github.com/nesk/akkurate/issues/29))

## [0.7.0]

### ⚠️ Breaking changes

- [Scope control](https://kotlinlang.org/docs/type-safe-builders.html#scope-control-dslmarker) has been implemented for Akkurate's DSL ([#25](https://github.com/nesk/akkurate/issues/25))

### Fixed

- Accessors for mutable properties are no longer improperly cast. ([#22](https://github.com/nesk/akkurate/issues/22))

## [0.6.0] - 2023-12-12

### Added

- Arrow integration to convert Akkurate validation results to typed errors ([#20](https://github.com/nesk/akkurate/issues/20)) 

## [0.5.0] - 2023-11-28

### Added

- New configuration option to fail on the first constraint violation ([#19](https://github.com/nesk/akkurate/issues/19))

## [0.4.0] - 2023-10-30

### ⚠️ Breaking changes

- The `Configuration` class is now instantiated through a builder DSL ([#13](https://github.com/nesk/akkurate/issues/13))
- Mark the API of the KSP plugin as experimental ([#13](https://github.com/nesk/akkurate/issues/13))
- Remove unused `MutablePath` type alias

### Added

- Support iterating over nullable iterables, like `Validatable<Iterable<*>?>`. ([#16](https://github.com/nesk/akkurate/issues/16))
- Add constraints to validate additional JVM types: `LocalDate`, `Duration` and `Period` ([#7](https://github.com/nesk/akkurate/issues/7))

## [0.3.0] - 2023-10-16

### ⚠️ Breaking changes

- Validation accessors are generated only for public properties. ([#11](https://github.com/nesk/akkurate/issues/11) [#15](https://github.com/nesk/akkurate/issues/15))
- Accessors are now generated for the properties of the implemented interface when possible, not for the implementation. ([#11](https://github.com/nesk/akkurate/issues/11))

### Added

- Provide built-in accessors for `kotlin` and `kotlin.collections` packages ([#11](https://github.com/nesk/akkurate/issues/11))
- Support generating accessors for generic types ([#10](https://github.com/nesk/akkurate/issues/10))
- A KDoc is provided for each validatable accessor ([#6](https://github.com/nesk/akkurate/issues/6))

### Fixed

- Validatable accessors are no longer generated for extension properties 
- Skip the generation of accessors in the `kotlin` package, avoiding compilation failures.

### Changed

- Propagate the `@Validate` annotation to the nested classes ([#11](https://github.com/nesk/akkurate/issues/11))

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

[Unreleased]: https://github.com/nesk/akkurate/compare/0.9.1...HEAD
[0.9.1]: https://github.com/nesk/akkurate/compare/0.9.0...0.9.1
[0.9.0]: https://github.com/nesk/akkurate/compare/0.8.0...0.9.0
[0.8.0]: https://github.com/nesk/akkurate/compare/0.7.0...0.8.0
[0.7.0]: https://github.com/nesk/akkurate/compare/0.6.0...0.7.0
[0.6.0]: https://github.com/nesk/akkurate/compare/0.5.0...0.6.0
[0.5.0]: https://github.com/nesk/akkurate/compare/0.4.0...0.5.0
[0.4.0]: https://github.com/nesk/akkurate/compare/0.3.0...0.4.0
[0.3.0]: https://github.com/nesk/akkurate/compare/0.2.0...0.3.0
[0.2.0]: https://github.com/nesk/akkurate/compare/0.1.1...0.2.0
[0.1.1]: https://github.com/nesk/akkurate/compare/0.1.0...0.1.1
[0.1.0]: https://github.com/nesk/akkurate/releases/tag/0.1.0
