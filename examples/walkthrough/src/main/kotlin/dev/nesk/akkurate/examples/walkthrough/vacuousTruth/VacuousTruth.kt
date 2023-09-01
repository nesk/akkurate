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
