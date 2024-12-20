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

public class ConstraintViolation(public override val message: String, public override val path: Path) : ConstraintDescriptor {
    public operator fun component1(): String = message
    public operator fun component2(): Path = path

    internal fun copy(path: Path = this.path) = ConstraintViolation(message, path)

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other == null || this::class != other::class) return false

        other as ConstraintViolation

        if (message != other.message) return false
        return path == other.path
    }

    override fun hashCode(): Int {
        var result = message.hashCode()
        result = 31 * result + path.hashCode()
        return result
    }

    override fun toString(): String = "ConstraintViolation(message='$message', path=$path)"
}
