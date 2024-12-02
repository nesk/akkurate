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

package dev.nesk.akkurate.ktor.server

import dev.nesk.akkurate.constraints.ConstraintViolation
import kotlinx.serialization.KSerializer
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder

@Serializable
@SerialName("dev.nesk.akkurate.constraints.ConstraintViolation")
private class ConstraintViolationSurrogate(val message: String, val path: String)

internal object ConstraintViolationSerializer : KSerializer<ConstraintViolation> {
    override val descriptor: SerialDescriptor = ConstraintViolationSurrogate.serializer().descriptor

    override fun serialize(encoder: Encoder, value: ConstraintViolation) {
        val surrogate = ConstraintViolationSurrogate(
            value.message,
            value.path
                .joinToString(".")
                .trim('.')
                .replace("\\.+".toRegex(), ".")
        )
        encoder.encodeSerializableValue(ConstraintViolationSurrogate.serializer(), surrogate)
    }

    override fun deserialize(decoder: Decoder): ConstraintViolation {
        val surrogate = decoder.decodeSerializableValue(ConstraintViolationSurrogate.serializer())
        return ConstraintViolation(surrogate.message, surrogate.path.split("."))
    }
}
