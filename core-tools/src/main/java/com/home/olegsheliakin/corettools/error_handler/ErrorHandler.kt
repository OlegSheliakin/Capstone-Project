package com.home.olegsheliakin.corettools.error_handler

import home.oleg.placesnearme.core_presentation.base.ErrorEvent

/**
 * Created by Oleg Sheliakin on 27.10.2018.
 * Contact me by email - olegsheliakin@gmail.com
 */
interface ErrorHandler {
    fun handle(throwable: Throwable): ErrorEvent
}
