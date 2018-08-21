package home.oleg.placesnearme.presentation.feature.map.viewmodel;

import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

import home.oleg.placenearme.interactors.GetVenuesInteractor;
import home.oleg.placesnearme.presentation.base.HideLoadingAction;
import home.oleg.placesnearme.presentation.base.ShowErrorAction;
import home.oleg.placesnearme.presentation.base.ShowLoadingAction;
import home.oleg.placesnearme.presentation.base.ViewAction;
import home.oleg.placesnearme.presentation.feature.map.view.MapView;
import home.oleg.placesnearme.presentation.feature.map.view.ShowDataAction;
import home.oleg.placesnearme.presentation.viewobjects.VenueViewObject;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by Oleg on 10.08.2018.
 */
public class MapViewModel extends ViewModel {

    private final GetVenuesInteractor interactor;

    private MutableLiveData<ViewAction<MapView>> observer = new MutableLiveData<>();
    private Disposable disposable;

    public MapViewModel(GetVenuesInteractor interactor) {
        this.interactor = interactor;
        init();
    }

    public MutableLiveData<ViewAction<MapView>> observeState() {
        return observer;
    }

    public void getRecommendedVenues() {
        disposable = interactor.getRecommendedVenues()
                .map(VenueViewObject::mapFrom)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe(d -> observer.setValue(new ShowLoadingAction<>()))
                //.doFinally(() -> observer.setValue(new HideLoadingAction<>()))
                .subscribe(venueViewObjects -> {
                            observer.setValue(new ShowDataAction(venueViewObjects));
                        },
                        throwable -> {
                            observer.setValue(new ShowErrorAction<>(throwable));
                        });
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        if (disposable != null) {
            disposable.dispose();
        }
    }

    private void init() {
        getRecommendedVenues();
    }

}
