package home.oleg.placesnearme.feature_map.viewmodel;

import home.oleg.placenearme.interactors.GetUserLocation;
import home.oleg.placesnearme.core_presentation.base.BaseViewModel;
import home.oleg.placesnearme.core_presentation.view_actions.ViewActions;
import home.oleg.placesnearme.feature_map.view.UserLocationView;
import home.oleg.placesnearme.feature_map.view_action.ShowUserLocationAction;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by Oleg Sheliakin on 17.09.2018.
 * Contact me by email - olegsheliakin@gmail.com
 */
public class UserLocationViewModel extends BaseViewModel<UserLocationView> {

    private final GetUserLocation userLocationInteractor;

    public UserLocationViewModel(GetUserLocation userLocationInteractor) {
        this.userLocationInteractor = userLocationInteractor;
    }

    public void getUserLocation() {
        addToDisposables(userLocationInteractor.getUserLocation()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        location -> setAction(ShowUserLocationAction.create(location)),
                        throwable -> setAction(ViewActions.error(throwable)))
        );
    }

}
