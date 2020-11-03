package com.lucasrodrigues.myfinances.utils

import java.lang.reflect.ParameterizedType
import kotlin.reflect.KClass

@Suppress("UNCHECKED_CAST")
fun <T : Any> Any.genericTypeClass(position: Int = 0): KClass<T> {
    return ((javaClass.genericSuperclass as ParameterizedType)
        .actualTypeArguments[position] as Class<T>).kotlin
}