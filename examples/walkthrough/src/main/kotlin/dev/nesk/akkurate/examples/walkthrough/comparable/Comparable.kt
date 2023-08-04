package dev.nesk.akkurate.examples.walkthrough.comparable

import dev.nesk.akkurate.Validator
import dev.nesk.akkurate.annotations.Validate
import dev.nesk.akkurate.constraints.constrain
import dev.nesk.akkurate.constraints.otherwise
import dev.nesk.akkurate.examples.walkthrough.comparable.validation.accessors.password
import dev.nesk.akkurate.examples.walkthrough.comparable.validation.accessors.passwordConfirmation

// Sometimes you need to compare a value to another one inside your payload. Since the values
// are wrapped in `Validatable` instances, comparing two values can easily become verbose when
// using the `unwrap` method or destructuring.

// Here we have a `UserRegistration` class, the user has to provide the same password in
// both `password` and `passwordConfirmation` fields.

@Validate
data class UserRegistration(val password: String, val passwordConfirmation: String)

val validateUserRegistration = Validator<UserRegistration> {

    // We can compare the passwords by using `unwrap`, but it is quite verbose.
    constrain { passwordConfirmation.unwrap() == password.unwrap() } otherwise { "Passwords are not matching" }

    // Fortunately, you can be more concise and directly compare the `Validatable` wrappers, which will have the same effect!
    constrain { passwordConfirmation == password } otherwise { "Passwords are not matching" }

}
