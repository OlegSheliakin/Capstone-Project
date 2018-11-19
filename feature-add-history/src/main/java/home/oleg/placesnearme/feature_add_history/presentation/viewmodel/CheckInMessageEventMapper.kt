package home.oleg.placesnearme.feature_add_history.presentation.viewmodel

import com.oleg.feature_add_history.R
import com.smedialink.common.base.MessageEvent
import home.oleg.placesnearme.corettools.resource.ResourceProvider
import javax.inject.Inject

/**
 * Created by Oleg Sheliakin on 06.11.2018.
 * Contact me by email - olegsheliakin@gmail.com
 */

class CheckInMessageEventMapper @Inject constructor(private val resourceProvider: ResourceProvider) {

    fun map(isCheckedIn: Boolean): MessageEvent {
        val text = if (isCheckedIn) {
            resourceProvider.getString(R.string.add_history_message_success_added)
        } else {
            resourceProvider.getString(R.string.add_history_message_success_removed)
        }

        return MessageEvent(text = text)
    }

}