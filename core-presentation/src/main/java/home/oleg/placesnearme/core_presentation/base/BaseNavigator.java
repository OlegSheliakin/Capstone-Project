package home.oleg.placesnearme.core_presentation.base;

import android.app.Activity;
import android.arch.lifecycle.Lifecycle;
import android.arch.lifecycle.LifecycleObserver;
import android.arch.lifecycle.OnLifecycleEvent;

public class BaseNavigator implements LifecycleObserver {

    private Activity activity;

    public void attach(Activity activity) {
        this.activity = activity;
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_PAUSE)
    public void detach() {
        activity = null;
    }


}
