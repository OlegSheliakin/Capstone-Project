package com.smedialink.feature_add_favorite

import home.oleg.placesnearme.core_presentation.base.MessageEvent
import home.oleg.placesnearme.core_presentation.provider.ResourceProvider
import javax.inject.Inject

/**
 * Created by Oleg Sheliakin on 06.11.2018.
 * Contact me by email - olegsheliakin@gmail.com
 */

class FavoriteMessageEventMapper @Inject constructor(
        private val resourceProvider: ResourceProvider){

    fun map(isfavorite: Boolean) : MessageEvent {
        val text = if (isfavorite) {
            resourceProvider.getString(R.string.add_favorite_message_success_added)
        } else {
            resourceProvider.getString(R.string.add_favorite_message_success_removed)
        }

        return MessageEvent(text = text)
    }

}