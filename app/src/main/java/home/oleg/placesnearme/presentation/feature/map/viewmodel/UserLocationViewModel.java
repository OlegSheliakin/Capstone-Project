package home.oleg.placesnearme.presentation.feature.map.viewmodel;

import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

import com.smedialink.common.function.Action;

import home.oleg.placesnearme.presentation.feature.map.view.MapView;
import home.oleg.placesnearme.presentation.feature.map.view.UserLocationView;

/**
 * Created by Oleg Sheliakin on 17.09.2018.
 * Contact me by email - olegsheliakin@gmail.com
 */
public class UserLocationViewModel extends ViewModel {

    private final MutableLiveData<Action<UserLocationView>> observer = new MutableLiveData<>();

    public MutableLiveData<Action<UserLocationView>> observer() {
        return observer;
    }

}
