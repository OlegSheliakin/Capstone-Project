package home.oleg.placesnearme.corettools.error_handler

import com.smedialink.common.base.ErrorEvent

/**
 * Created by Oleg Sheliakin on 27.10.2018.
 * Contact me by email - olegsheliakin@gmail.com
 */
interface ErrorHandler {
    fun handle(throwable: Throwable): ErrorEvent
}
