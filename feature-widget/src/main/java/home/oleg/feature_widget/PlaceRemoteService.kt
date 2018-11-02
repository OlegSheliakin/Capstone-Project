package home.oleg.feature_widget

import android.content.Intent
import android.widget.RemoteViewsService

class PlaceRemoteService : RemoteViewsService() {

    override fun onGetViewFactory(intent: Intent): RemoteViewsService.RemoteViewsFactory {
        return ListRemoteViewsFactory(applicationContext, intent)
    }
}
