package home.oleg.placesnearme.feature_map.viewmodel;

import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

import home.oleg.placenearme.interactors.GetUserLocation;
import home.oleg.placesnearme.feature_map.state.LocationViewState;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by Oleg Sheliakin on 17.09.2018.
 * Contact me by email - olegsheliakin@gmail.com
 */
public class UserLocationViewModel extends ViewModel {

    private final GetUserLocation userLocationInteractor;
    private final MutableLiveData<LocationViewState> state = new MutableLiveData<>();
    private Disposable disposable;

    public UserLocationViewModel(GetUserLocation userLocationInteractor) {
        this.userLocationInteractor = userLocationInteractor;

        state.setValue(LocationViewState.initial());
    }

    public MutableLiveData<LocationViewState> getState() {
        return state;
    }

    public void getUserLocation() {
        if (disposable != null && !disposable.isDisposed()) {
            disposable.dispose();
        }

        disposable = userLocationInteractor.getUserLocation()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        location -> {
                            state.setValue(LocationViewState.showLocation(location));
                        },
                        ignored -> {
                        });
    }
}
