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

public typealias ConstraintViolation = GenericConstraintViolation<DefaultMetadataType>

public class GenericConstraintViolation<MetadataType>(
    public override val message: String,
    public override val path: Path,
    override val metadata: MetadataType
) : GenericConstraintDescriptor<MetadataType> {
    public operator fun component1(): String = message
    public operator fun component2(): Path = path

    internal fun copy(path: Path = this.path): GenericConstraintViolation<MetadataType> = GenericConstraintViolation(message, path, metadata)
    internal fun <NEW_META> copy(metadata: NEW_META): GenericConstraintViolation<NEW_META> = GenericConstraintViolation(message, path, metadata)

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other == null || this::class != other::class) return false

        other as GenericConstraintViolation<MetadataType>

        if (message != other.message) return false
        if (metadata != other.metadata) return false
        return path == other.path
    }

    override fun hashCode(): Int {
        var result = message.hashCode()
        result = 31 * result + path.hashCode()
        return result
    }

    override fun toString(): String = "ConstraintViolation(message='$message', path=$path, metadata=$metadata)"

    public companion object {
        public operator fun invoke(message: String, path: Path): ConstraintViolation =
            GenericConstraintViolation(message, path, emptyMap())
    }
}
