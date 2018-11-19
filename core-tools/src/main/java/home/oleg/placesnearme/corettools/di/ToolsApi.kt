package home.oleg.placesnearme.corettools.di

import android.content.Context
import com.smedialink.common.util.SingletonHolder
import home.oleg.placesnearme.corettools.error_handler.ErrorHandler
import home.oleg.placesnearme.corettools.logger.Logger
import home.oleg.placesnearme.corettools.resource.ResourceProvider

interface ToolsApi {

    val errorHandler: ErrorHandler

    val resourceProvider: ResourceProvider

    val logger: Logger

    companion object : SingletonHolder<ToolsApi, Context>(creator = {
        DaggerToolsComponent
                .builder()
                .bindContext(it)
                .build()
    })

}