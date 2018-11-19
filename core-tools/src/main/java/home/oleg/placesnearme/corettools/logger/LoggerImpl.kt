package home.oleg.placesnearme.corettools.logger

import timber.log.Timber
import javax.inject.Inject

/**
 * Created by Oleg Sheliakin on 13.11.2018.
 * Contact me by email - olegsheliakin@gmail.com
 */
class LoggerImpl @Inject constructor(): Logger {

    override fun error(error: Throwable) = Timber.e(error)
}