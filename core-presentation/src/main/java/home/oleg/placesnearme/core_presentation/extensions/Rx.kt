package home.oleg.placesnearme.core_presentation.extensions

import io.reactivex.Single
import io.reactivex.schedulers.Schedulers

/**
 * Created by Oleg Sheliakin on 06.11.2018.
 * Contact me by email - olegsheliakin@gmail.com
 */

fun <T> Single<T>.io(): Single<T> {
    return subscribeOn(Schedulers.io())
}

fun <T> Single<T>.computation(): Single<T> {
    return subscribeOn(Schedulers.io())
}

