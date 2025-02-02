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

import dev.nesk.akkurate.constraints.GenericConstraint
import dev.nesk.akkurate.validatables.GenericValidatable

// Keep this constraint only available to the JVM target to avoid potential issues.
// For example, the JS target fails to properly determine the fractional count of a Float.
// We'll see later to make this constraint available to all targets.
@JvmName("floatHasFractionalCountEqualTo")
public fun <MetadataType> GenericValidatable<Float?, MetadataType>.hasFractionalCountEqualTo(count: Int): GenericConstraint<MetadataType> =
    constrainDecimalPart(count) otherwiseFractionalCountEqualTo count
