package com.smedialink.common.base

/**
 * Created by Oleg Sheliakin on 27.10.2018.
 * Contact me by email - olegsheliakin@gmail.com
 */
data class ErrorEvent(val error: Throwable, val errorText: String) : LiveEvent()
