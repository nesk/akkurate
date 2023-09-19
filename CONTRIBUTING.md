# Contributing to Akkurate

Before making any meaningful changes to Akkurate, we strongly recommend you to create an issue and discuss them with us.
We definitely don't want you to write code and get your Pull Request closed because the changes are out-of-scope.

You should be safe if your changes are about fixing a bug, or adding a missing constraint on a type included in Kotlin's
standard library; however, we still recommend you to create an issue just to be sure.

## Code style

Before submitting your changes, make sure you've applied code formatting and removed unused imports.

## Testing

When making some changes to existing code, make sure **all the tests** still pass, and adapt them when needed.

If you're fixing a bug, create a new test reproducing the bug to avoid future regressions.

New code should be tested with a significant code coverage score (you can check it by using **Run | Run with Coverage**
in IntelliJ). 
