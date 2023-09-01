/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements. See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License. You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

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
