package dev.nesk.akkurate.api

import dev.nesk.akkurate.Company
import dev.nesk.akkurate.Plan
import dev.nesk.akkurate.User
import dev.nesk.akkurate.api.Constraint
import dev.nesk.akkurate.api.Validatable
import java.time.Instant

// String
fun Validatable<String>.empty(): Constraint<String> = TODO()
fun Validatable<String>.notEmpty(): Constraint<String> = TODO()
fun Validatable<String>.minLength(length: Int): Constraint<String> = TODO()
fun Validatable<String>.maxLength(length: Int): Constraint<String> = TODO()

// Iterables
fun <T> Validatable<out Collection<T>>.minSize(length: Int): Constraint<Collection<T>> = TODO()
fun <T> Validatable<out Collection<T>>.maxSize(length: Int): Constraint<Collection<T>> = TODO()

// Instant
fun Validatable<Instant>.before(other: Instant): Constraint<Instant> = TODO()
