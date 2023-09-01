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

package dev.nesk.akkurate.constraints.builders

import dev.nesk.akkurate.constraints.Constraint
import dev.nesk.akkurate.constraints.constrain
import dev.nesk.akkurate.constraints.otherwise
import dev.nesk.akkurate.validatables.Validatable

public fun <T> Validatable<T?>.isNull(): Constraint = constrain { it == null } otherwise { "Must be null" }
public fun <T> Validatable<T?>.isNotNull(): Constraint = constrain { it != null } otherwise { "Must not be null" }

public fun <T> Validatable<T?>.isEqualTo(other: T?): Constraint = constrain { it == other } otherwise { "Must be equal to \"$other\"" }
public fun <T> Validatable<T?>.isEqualTo(other: Validatable<T>): Constraint = constrain { it == other.unwrap() } otherwise { "Must be equal to \"${other.unwrap()}\"" }

public fun <T> Validatable<T?>.isNotEqualTo(other: T?): Constraint = constrain { it != other } otherwise { "Must be different from \"$other\"" }
public fun <T> Validatable<T?>.isNotEqualTo(other: Validatable<T>): Constraint = constrain { it != other.unwrap() } otherwise { "Must be different from \"${other.unwrap()}\"" }

public fun <T> Validatable<T?>.isIdenticalTo(other: T?): Constraint = constrain { it === other } otherwise { "Must be identical to \"$other\"" }
public fun <T> Validatable<T?>.isNotIdenticalTo(other: T?): Constraint = constrain { it !== other } otherwise { "Must not be identical to \"$other\"" }
