package com.veyndan.android

import android.content.ContentValues
import kotlin.reflect.KProperty

inline operator fun <reified V> ContentValues.getValue(thisRef: Any?, property: KProperty<*>): V {
    val key = property.name
    return if (containsKey(key))
        get(key) as V
    else
        throw NoSuchElementException("Key $key is missing in the ContentValues.")
}
