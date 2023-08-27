package dev.nesk.akkurate.examples.walkthrough.vacuousTruth

import dev.nesk.akkurate.Validator
import dev.nesk.akkurate.annotations.Validate
import dev.nesk.akkurate.constraints.builders.isInPast
import dev.nesk.akkurate.examples.walkthrough.vacuousTruth.validation.accessors.birthDate
import java.time.Instant

// The vast majority of the builtin constraints apply the Vacuous Truth principle.
// https://en.wikipedia.org/wiki/Vacuous_truth#In_computer_programming

// If a constraint is applied on a nullable value, and this value is `null`, then
// the constraint cannot fail and will be satisfied. Actually, it won't even be
// executed.

@Validate
data class User(val birthDate: Instant?)

val validateBook = Validator<User> {

    // A user might not want to provide its birthdate. If there is no birthdate (a.k.a. `null`)
    // then the constraint is ALWAYS satisfied. However, if a birthdate is specified, it must be in the past.
    birthDate.isInPast()

}
