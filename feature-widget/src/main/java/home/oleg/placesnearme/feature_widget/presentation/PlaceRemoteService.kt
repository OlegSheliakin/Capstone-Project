package home.oleg.placesnearme.feature_widget.presentation

import android.content.Intent
import android.widget.RemoteViewsService
import home.oleg.placesnearme.feature_widget.presentation.ui.ListRemoteViewsFactory

class PlaceRemoteService : RemoteViewsService() {

    override fun onGetViewFactory(intent: Intent): RemoteViewsService.RemoteViewsFactory {
        return ListRemoteViewsFactory(applicationContext, intent)
    }
}
