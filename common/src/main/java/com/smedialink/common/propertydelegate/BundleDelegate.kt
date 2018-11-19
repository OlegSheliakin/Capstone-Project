package com.smedialink.common.propertydelegate

import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty

class BundleDelegate<T : Any>(private val key: String?) : ReadWriteProperty<Fragment, T?> {

    override fun getValue(thisRef: Fragment, property: KProperty<*>): T? {
        return thisRef.arguments?.get(key) as? T
    }

    override fun setValue(thisRef: Fragment, property: KProperty<*>, value: T?) {
        val key_ = key ?: "key_${property.name}"

        thisRef.arguments = bundleOf(Pair(key_, value))
    }

}

fun <T : Any> bundle(key: String? = null): BundleDelegate<T> = BundleDelegate(key)