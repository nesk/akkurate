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

package dev.nesk.akkurate.ksp

import com.google.devtools.ksp.symbol.*
import com.squareup.kotlinpoet.ClassName
import com.squareup.kotlinpoet.MemberName
import com.squareup.kotlinpoet.ParameterizedTypeName.Companion.parameterizedBy
import com.squareup.kotlinpoet.TypeName

internal fun KSDeclaration.toClassName(): ClassName {
    val simpleNames = mutableListOf(simpleName)

    var parent = parentDeclaration
    while (parent != null) {
        simpleNames += parent.simpleName
        parent = parent.parentDeclaration
    }

    return ClassName(packageName.asString(), *simpleNames.reversed().map(KSName::asString).toTypedArray())
}

internal fun KSDeclaration.toTypeName(): TypeName = toClassName()

internal fun KSType.toTypeName(): TypeName {
    val className = declaration.toClassName()
    val typeName = if (arguments.isEmpty()) className else {
        className.parameterizedBy(arguments.mapNotNull { it.type }.map { it.toTypeName() })
    }
    return typeName.copy(nullable = nullability == Nullability.NULLABLE)
}

internal fun KSTypeReference.toTypeName(): TypeName = resolve().toTypeName()

internal fun KSPropertyDeclaration.toMemberName() = MemberName(parentDeclaration!!.toClassName(), simpleName.asString())
