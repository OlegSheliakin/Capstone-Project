package com.smedialink.common.ext

import androidx.fragment.app.Fragment
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer

/**
 * Created by Oleg Sheliakin on 06.11.2018.
 * Contact me by email - olegsheliakin@gmail.com
 */
fun <T: Any> LiveData<T>.observeExt(target: LifecycleOwner, onChange: (T) -> Unit) {
    observe(target, Observer {
        it?.let(onChange)
    })
}

fun <T : Any> LiveData<T>.observeExt(target: Fragment, onChange: (T) -> Unit) {
    observe(target.viewLifecycleOwner, Observer {
        onChange(it)
    })
}

fun <T : Any> MutableLiveData<T>.reduceState(reduce: (T) -> T) {
    value?.let {
        value = reduce(it)
    }
}