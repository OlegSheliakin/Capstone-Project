package com.smedialink.common.propertydelegate

import io.reactivex.disposables.Disposable
import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty

/**
 * Created by Oleg Sheliakin on 06.11.2018.
 * Contact me by email - olegsheliakin@gmail.com
 */

class DisposableDelegate : ReadWriteProperty<Any, Disposable?> {

    private var disposable: Disposable? = null

    override fun getValue(thisRef: Any, property: KProperty<*>): Disposable? {
        return disposable
    }

    override fun setValue(thisRef: Any, property: KProperty<*>, value: Disposable?) {
        disposable?.takeUnless { it.isDisposed }?.dispose()
        disposable = value
    }

}

fun disposableDelegate() : DisposableDelegate = DisposableDelegate()