package home.oleg.feature_widget;

import android.content.Intent;
import android.widget.RemoteViewsService;

public class PlaceRemoteService extends RemoteViewsService {

    @Override
    public RemoteViewsFactory onGetViewFactory(Intent intent) {
        return new ListRemoteViewsFactory(getApplicationContext(), intent);
    }
}
