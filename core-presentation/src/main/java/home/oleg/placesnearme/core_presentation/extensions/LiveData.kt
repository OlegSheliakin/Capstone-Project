package home.oleg.placesnearme.core_presentation.extensions

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer

/**
 * Created by Oleg Sheliakin on 06.11.2018.
 * Contact me by email - olegsheliakin@gmail.com
 */

fun <T> LiveData<T>.observeX(lifecycleOwner: LifecycleOwner, onChange: (T) -> Unit) {
    this.observe(lifecycleOwner, Observer {
        it?.let(onChange)
    })
}