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

package dev.nesk.akkurate.constraints

import dev.nesk.akkurate.Path
import dev.nesk.akkurate.validatables.DefaultMetadataType

public typealias ConstraintViolationSet = GenericConstraintViolationSet<DefaultMetadataType>

public class GenericConstraintViolationSet<MetadataType>(private val messages: Set<GenericConstraintViolation<MetadataType>>) : Set<GenericConstraintViolation<MetadataType>> by messages {
    public val byPath: Map<Path, Set<GenericConstraintViolation<MetadataType>>> by lazy { messages.groupBy { it.path }.mapValues { it.value.toSet() } }

    override fun equals(other: Any?): Boolean = messages == other

    override fun hashCode(): Int = messages.hashCode()

    override fun toString(): String = "ConstraintViolationSet(messages=$messages)"

}
