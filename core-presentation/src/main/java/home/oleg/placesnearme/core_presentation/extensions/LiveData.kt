package home.oleg.placesnearme.core_presentation.extensions

import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ComponentActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer

/**
 * Created by Oleg Sheliakin on 06.11.2018.
 * Contact me by email - olegsheliakin@gmail.com
 */

fun <T> LiveData<T>.observeNonNull(lifecycleOwner: LifecycleOwner, onChange: (T) -> Unit) {
    this.observe(lifecycleOwner, Observer {
        it?.let(onChange)
    })
}

fun <T : Any> ComponentActivity.observe(liveData: LiveData<T>, onChange: (T) -> Unit) {
    liveData.observe(this, Observer {
        it?.let(onChange)
    })
}

fun <T : Any> Fragment.observe(liveData: LiveData<T>, onChange: (T) -> Unit) {
    liveData.observe(viewLifecycleOwner, Observer {
        it?.let(onChange)
    })
}