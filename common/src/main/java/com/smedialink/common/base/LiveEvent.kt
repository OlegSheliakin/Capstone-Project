package com.smedialink.common.base

/**
 * Created by Oleg Sheliakin on 27.10.2018.
 * Contact me by email - olegsheliakin@gmail.com
 */
open class LiveEvent {
    var isHandled: Boolean = false
}

fun <T : LiveEvent> T.handle(action: (T) -> Unit) {
    if (!this.isHandled) {
        this.isHandled = true
        action(this)
    }
}
