# Constraints reference

%product% is bundled with all the essential validation constraints. Their role is to validate all the day-to-day Kotlin
objects, without having to write custom constraints for anything else than business logic. {id="introduction"}

> This is a bold assertion and, let's be honest, it might not be true at the moment. _But we want it to be true!_ So let
> us know if you're missing a constraint, [and report it on the GitHub repository.](%github_product_url%/issues)

## Basic constraints

- [isNull](https://akkurate.dev/api/akkurate-core/dev.nesk.akkurate.constraints.builders/is-null.html)
- [isNotNull](https://akkurate.dev/api/akkurate-core/dev.nesk.akkurate.constraints.builders/is-not-null.html)
- [isEqualTo](https://akkurate.dev/api/akkurate-core/dev.nesk.akkurate.constraints.builders/is-equal-to.html)
- [isNotEqualTo](https://akkurate.dev/api/akkurate-core/dev.nesk.akkurate.constraints.builders/is-not-equal-to.html)
- [isIdenticalTo](https://akkurate.dev/api/akkurate-core/dev.nesk.akkurate.constraints.builders/is-identical-to.html)
- [isNotIdenticalTo](https://akkurate.dev/api/akkurate-core/dev.nesk.akkurate.constraints.builders/is-not-identical-to.html)
- [isTrue](https://akkurate.dev/api/akkurate-core/dev.nesk.akkurate.constraints.builders/is-true.html)
- [isNotTrue](https://akkurate.dev/api/akkurate-core/dev.nesk.akkurate.constraints.builders/is-not-true.html)
- [isFalse](https://akkurate.dev/api/akkurate-core/dev.nesk.akkurate.constraints.builders/is-false.html)
- [isNotFalse](https://akkurate.dev/api/akkurate-core/dev.nesk.akkurate.constraints.builders/is-not-false.html)

## String constraints

- [isEmpty](https://akkurate.dev/api/akkurate-core/dev.nesk.akkurate.constraints.builders/is-empty.html)
- [isNotEmpty](https://akkurate.dev/api/akkurate-core/dev.nesk.akkurate.constraints.builders/is-not-empty.html)
- [isBlank](https://akkurate.dev/api/akkurate-core/dev.nesk.akkurate.constraints.builders/is-blank.html)
- [isNotBlank](https://akkurate.dev/api/akkurate-core/dev.nesk.akkurate.constraints.builders/is-not-blank.html)
- [hasLengthEqualTo](https://akkurate.dev/api/akkurate-core/dev.nesk.akkurate.constraints.builders/has-length-equal-to.html)
- [hasLengthNotEqualTo](https://akkurate.dev/api/akkurate-core/dev.nesk.akkurate.constraints.builders/has-length-not-equal-to.html)
- [hasLengthLowerThan](https://akkurate.dev/api/akkurate-core/dev.nesk.akkurate.constraints.builders/has-length-lower-than.html)
- [hasLengthLowerThanOrEqualTo](https://akkurate.dev/api/akkurate-core/dev.nesk.akkurate.constraints.builders/has-length-lower-than-or-equal-to.html)
- [hasLengthGreaterThan](https://akkurate.dev/api/akkurate-core/dev.nesk.akkurate.constraints.builders/has-length-greater-than.html)
- [hasLengthGreaterThanOrEqualTo](https://akkurate.dev/api/akkurate-core/dev.nesk.akkurate.constraints.builders/has-length-greater-than-or-equal-to.html)
- [hasLengthBetween](https://akkurate.dev/api/akkurate-core/dev.nesk.akkurate.constraints.builders/has-length-between.html)
- [isStartingWith](https://akkurate.dev/api/akkurate-core/dev.nesk.akkurate.constraints.builders/is-starting-with.html)
- [isNotStartingWith](https://akkurate.dev/api/akkurate-core/dev.nesk.akkurate.constraints.builders/is-not-starting-with.html)
- [isEndingWith](https://akkurate.dev/api/akkurate-core/dev.nesk.akkurate.constraints.builders/is-ending-with.html)
- [isNotEndingWith](https://akkurate.dev/api/akkurate-core/dev.nesk.akkurate.constraints.builders/is-not-ending-with.html)
- [isContaining](https://akkurate.dev/api/akkurate-core/dev.nesk.akkurate.constraints.builders/is-containing.html)
- [isNotContaining](https://akkurate.dev/api/akkurate-core/dev.nesk.akkurate.constraints.builders/is-not-containing.html)
- [isMatching](https://akkurate.dev/api/akkurate-core/dev.nesk.akkurate.constraints.builders/is-matching.html)
- [isNotMatching](https://akkurate.dev/api/akkurate-core/dev.nesk.akkurate.constraints.builders/is-not-matching.html)

## Number constraints

- [isNotNaN](https://akkurate.dev/api/akkurate-core/dev.nesk.akkurate.constraints.builders/is-not-na-n.html)
- [isFinite](https://akkurate.dev/api/akkurate-core/dev.nesk.akkurate.constraints.builders/is-finite.html)
- [isInfinite](https://akkurate.dev/api/akkurate-core/dev.nesk.akkurate.constraints.builders/is-infinite.html)
- [isNegative](https://akkurate.dev/api/akkurate-core/dev.nesk.akkurate.constraints.builders/is-negative.html)
- [isNegativeOrZero](https://akkurate.dev/api/akkurate-core/dev.nesk.akkurate.constraints.builders/is-negative-or-zero.html)
- [isPositive](https://akkurate.dev/api/akkurate-core/dev.nesk.akkurate.constraints.builders/is-positive.html)
- [isPositiveOrZero](https://akkurate.dev/api/akkurate-core/dev.nesk.akkurate.constraints.builders/is-positive-or-zero.html)
- [isLowerThan](https://akkurate.dev/api/akkurate-core/dev.nesk.akkurate.constraints.builders/is-lower-than.html)
- [isLowerThanOrEqualTo](https://akkurate.dev/api/akkurate-core/dev.nesk.akkurate.constraints.builders/is-lower-than-or-equal-to.html)
- [isGreaterThan](https://akkurate.dev/api/akkurate-core/dev.nesk.akkurate.constraints.builders/is-greater-than.html)
- [isGreaterThanOrEqualTo](https://akkurate.dev/api/akkurate-core/dev.nesk.akkurate.constraints.builders/is-greater-than-or-equal-to.html)
- [isBetween](https://akkurate.dev/api/akkurate-core/dev.nesk.akkurate.constraints.builders/is-between.html)
- [hasIntegralCountEqualTo](https://akkurate.dev/api/akkurate-core/dev.nesk.akkurate.constraints.builders/has-integral-count-equal-to.html)
- [hasFractionalCountEqualTo](https://akkurate.dev/api/akkurate-core/dev.nesk.akkurate.constraints.builders/has-fractional-count-equal-to.html)

## Collection constraints

- [isEmpty](https://akkurate.dev/api/akkurate-core/dev.nesk.akkurate.constraints.builders/is-empty.html)
- [isNotEmpty](https://akkurate.dev/api/akkurate-core/dev.nesk.akkurate.constraints.builders/is-not-empty.html)
- [hasSizeEqualTo](https://akkurate.dev/api/akkurate-core/dev.nesk.akkurate.constraints.builders/has-size-equal-to.html)
- [hasSizeNotEqualTo](https://akkurate.dev/api/akkurate-core/dev.nesk.akkurate.constraints.builders/has-size-not-equal-to.html)
- [hasSizeLowerThan](https://akkurate.dev/api/akkurate-core/dev.nesk.akkurate.constraints.builders/has-size-lower-than.html)
- [hasSizeLowerThanOrEqualTo](https://akkurate.dev/api/akkurate-core/dev.nesk.akkurate.constraints.builders/has-size-lower-than-or-equal-to.html)
- [hasSizeGreaterThan](https://akkurate.dev/api/akkurate-core/dev.nesk.akkurate.constraints.builders/has-size-greater-than.html)
- [hasSizeGreaterThanOrEqualTo](https://akkurate.dev/api/akkurate-core/dev.nesk.akkurate.constraints.builders/has-size-greater-than-or-equal-to.html)
- [hasSizeBetween](https://akkurate.dev/api/akkurate-core/dev.nesk.akkurate.constraints.builders/has-size-between.html)
- [isContaining](https://akkurate.dev/api/akkurate-core/dev.nesk.akkurate.constraints.builders/is-containing.html)
- [isNotContaining](https://akkurate.dev/api/akkurate-core/dev.nesk.akkurate.constraints.builders/is-not-containing.html)
- [isContainingKey](https://akkurate.dev/api/akkurate-core/dev.nesk.akkurate.constraints.builders/is-containing-key.html)
- [isNotContainingKey](https://akkurate.dev/api/akkurate-core/dev.nesk.akkurate.constraints.builders/is-not-containing-key.html)
- [isContainingValue](https://akkurate.dev/api/akkurate-core/dev.nesk.akkurate.constraints.builders/is-containing-value.html)
- [isNotContainingValue](https://akkurate.dev/api/akkurate-core/dev.nesk.akkurate.constraints.builders/is-not-containing-value.html)
- [hasNoDuplicates](https://akkurate.dev/api/akkurate-core/dev.nesk.akkurate.constraints.builders/has-no-duplicates.html)

## `kotlin.time` constraints

- [isNegative](https://akkurate.dev/api/akkurate-core/dev.nesk.akkurate.constraints.builders/is-negative.html)
- [isNegativeOrZero](https://akkurate.dev/api/akkurate-core/dev.nesk.akkurate.constraints.builders/is-negative-or-zero.html)
- [isPositive](https://akkurate.dev/api/akkurate-core/dev.nesk.akkurate.constraints.builders/is-positive.html)
- [isPositiveOrZero](https://akkurate.dev/api/akkurate-core/dev.nesk.akkurate.constraints.builders/is-positive-or-zero.html)
- [isLowerThan](https://akkurate.dev/api/akkurate-core/dev.nesk.akkurate.constraints.builders/is-lower-than.html)
- [isLowerThanOrEqualTo](https://akkurate.dev/api/akkurate-core/dev.nesk.akkurate.constraints.builders/is-lower-than-or-equal-to.html)
- [isGreaterThan](https://akkurate.dev/api/akkurate-core/dev.nesk.akkurate.constraints.builders/is-greater-than.html)
- [isGreaterThanOrEqualTo](https://akkurate.dev/api/akkurate-core/dev.nesk.akkurate.constraints.builders/is-greater-than-or-equal-to.html)
- [isBetween](https://akkurate.dev/api/akkurate-core/dev.nesk.akkurate.constraints.builders/is-between.html)
- [isBetween](https://akkurate.dev/api/akkurate-core/dev.nesk.akkurate.constraints.builders/is-between.html)

## `java.time` constraints

- [isInPast](https://akkurate.dev/api/akkurate-core/dev.nesk.akkurate.constraints.builders/is-in-past.html)
- [isInPastOrIsPresent](https://akkurate.dev/api/akkurate-core/dev.nesk.akkurate.constraints.builders/is-in-past-or-is-present.html)
- [isInFuture](https://akkurate.dev/api/akkurate-core/dev.nesk.akkurate.constraints.builders/is-in-future.html)
- [isInFutureOrIsPresent](https://akkurate.dev/api/akkurate-core/dev.nesk.akkurate.constraints.builders/is-in-future-or-is-present.html)
- [isBefore](https://akkurate.dev/api/akkurate-core/dev.nesk.akkurate.constraints.builders/is-before.html)
- [isBeforeOrEqualTo](https://akkurate.dev/api/akkurate-core/dev.nesk.akkurate.constraints.builders/is-before-or-equal-to.html)
- [isAfter](https://akkurate.dev/api/akkurate-core/dev.nesk.akkurate.constraints.builders/is-after.html)
- [isAfterOrEqualTo](https://akkurate.dev/api/akkurate-core/dev.nesk.akkurate.constraints.builders/is-after-or-equal-to.html)
- [isNegative](https://akkurate.dev/api/akkurate-core/dev.nesk.akkurate.constraints.builders/is-negative.html)
- [isNegativeOrZero](https://akkurate.dev/api/akkurate-core/dev.nesk.akkurate.constraints.builders/is-negative-or-zero.html)
- [isPositive](https://akkurate.dev/api/akkurate-core/dev.nesk.akkurate.constraints.builders/is-positive.html)
- [isPositiveOrZero](https://akkurate.dev/api/akkurate-core/dev.nesk.akkurate.constraints.builders/is-positive-or-zero.html)
- [isLowerThan](https://akkurate.dev/api/akkurate-core/dev.nesk.akkurate.constraints.builders/is-lower-than.html)
- [isLowerThanOrEqualTo](https://akkurate.dev/api/akkurate-core/dev.nesk.akkurate.constraints.builders/is-lower-than-or-equal-to.html)
- [isGreaterThan](https://akkurate.dev/api/akkurate-core/dev.nesk.akkurate.constraints.builders/is-greater-than.html)
- [isGreaterThanOrEqualTo](https://akkurate.dev/api/akkurate-core/dev.nesk.akkurate.constraints.builders/is-greater-than-or-equal-to.html)
- [isBetween](https://akkurate.dev/api/akkurate-core/dev.nesk.akkurate.constraints.builders/is-between.html)

<seealso style="cards">
  <category ref="related">
    <a href="apply-constraints.md" />
  </category>
</seealso>
