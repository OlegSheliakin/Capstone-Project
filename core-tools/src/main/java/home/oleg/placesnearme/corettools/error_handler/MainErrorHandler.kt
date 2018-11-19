package home.oleg.placesnearme.corettools.error_handler

import com.home.oleg.corettools.R
import com.smedialink.common.base.ErrorEvent
import home.oleg.placesnearme.corettools.logger.Logger
import home.oleg.placesnearme.corettools.resource.ResourceProvider
import retrofit2.HttpException
import java.io.IOException
import java.net.SocketTimeoutException
import java.net.UnknownHostException
import java.util.*
import javax.inject.Inject

/**
 * Created by Oleg Sheliakin on 27.10.2018.
 * Contact me by email - olegsheliakin@gmail.com
 */
class MainErrorHandler @Inject constructor(
        private val logger: Logger,
        private val resourceProvider: ResourceProvider) : ErrorHandler {

    private val mapIfStringRes = HashMap<Class<out Throwable>, Int>()

    init {
        mapIfStringRes[SocketTimeoutException::class.java] = R.string.timeout_error
        mapIfStringRes[IOException::class.java] = R.string.no_internet_connection
        mapIfStringRes[UnknownHostException::class.java] = R.string.no_internet_connection
    }

    override fun handle(throwable: Throwable): ErrorEvent {
        logger.error(throwable)

        var stringRes: Int? = null

        if (throwable is HttpException) {
            when (throwable.code()) {
                429 -> stringRes = R.string.error_limit_exceeded
            }
        } else {
            stringRes = mapIfStringRes[throwable.javaClass]
        }

        if (stringRes == null) {
            stringRes = R.string.unexpected_error
        }

        val errorText = resourceProvider.getString(stringRes)

        return ErrorEvent(throwable, errorText)
    }

}
