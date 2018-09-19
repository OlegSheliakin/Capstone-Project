package home.oleg.placesnearme.presentation.feature.map.viewmodel;

import home.oleg.placenearme.interactors.GetUserLocationInteractor;
import home.oleg.placesnearme.presentation.base.BaseViewModel;
import home.oleg.placesnearme.presentation.feature.map.view.UserLocationView;
import home.oleg.placesnearme.presentation.view_action.ShowUserLocationAction;
import home.oleg.placesnearme.presentation.view_action.ViewActions;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by Oleg Sheliakin on 17.09.2018.
 * Contact me by email - olegsheliakin@gmail.com
 */
public class UserLocationViewModel extends BaseViewModel<UserLocationView> {

    private final GetUserLocationInteractor userLocationInteractor;

    public UserLocationViewModel(GetUserLocationInteractor userLocationInteractor) {
        this.userLocationInteractor = userLocationInteractor;
    }

    public void getUserLocation() {
        addToDisposables(userLocationInteractor.getUserLocation()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(location -> {
                            setAction(ShowUserLocationAction.create(location));
                        },
                        throwable -> {
                            setAction(ViewActions.error(throwable));
                        })
        );
    }

}
