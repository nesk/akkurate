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

package dev.nesk.akkurate.examples.walkthrough.customConstraints

import dev.nesk.akkurate.Validator
import dev.nesk.akkurate.annotations.Validate
import dev.nesk.akkurate.constraints.Constraint
import dev.nesk.akkurate.constraints.constrain
import dev.nesk.akkurate.constraints.constrainIfNotNull
import dev.nesk.akkurate.constraints.otherwise
import dev.nesk.akkurate.examples.walkthrough.customConstraints.validation.accessors.title
import dev.nesk.akkurate.validatables.Validatable

// If you need to do some custom validation, you can use the `constrain` function with a
// lambda returning `true` if the constraint is satisfied, or `false` if it isn't.
// Here we want to ensure the book title has at least two words in it.

@Validate
data class Book(val title: String)

val validateBookWithInlineConstraint = Validator<Book> {
    title.constrain { it.split(" ").size >= 2 } otherwise { "The title must contain at least two words" }
}

// If you use the same constraint multiple times, you can stop repeating yourself and
// extract it to an extension function. Doing this also allows you to pass parameters.

// When creating a named constraint, you should use a `Validatable` receiver with a
// nullable generic type, and apply the constraint with `constraintIfNotNull`. This
// allows you to use your constraint on nullable values and apply the concept
// of Vacuous Truth: if the wrapped value is null, then the constraint is always satisfied.

private fun Validatable<String?>.wordCountGreaterThanOrEqualTo(count: Int): Constraint =
    constrainIfNotNull { it.split(" ").size >= count } otherwise { "Must contain at least $count words" }

val validateBookWithNamedConstraint = Validator<Book> {

    // Note that your can also override message and path on custom constraints.
    title.wordCountGreaterThanOrEqualTo(2) otherwise { "Two words at least please" }

}
