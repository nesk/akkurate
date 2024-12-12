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

public fun Validatable<Boolean?>.isTrue(): Constraint = constrain { it == true } otherwise { "Must be true" }
public fun Validatable<Boolean?>.isNotTrue(): Constraint = constrain { it != true } otherwise { "Must not be true" }

public fun Validatable<Boolean?>.isFalse(): Constraint = constrain { it == false } otherwise { "Must be false" }
public fun Validatable<Boolean?>.isNotFalse(): Constraint = constrain { it != false } otherwise { "Must not be false" }
