package home.oleg.placesnearme.presentation.feature.map.viewmodel;

import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

import com.google.android.gms.maps.model.LatLng;
import com.smedialink.common.Optional;
import com.smedialink.common.function.Action;

import home.oleg.placenearme.interactors.GetRecomendedVenuesInteractor;
import home.oleg.placesnearme.presentation.base.ShowVenueDataAction;
import home.oleg.placesnearme.presentation.base.ViewActions;
import home.oleg.placesnearme.presentation.feature.map.view.MapView;
import home.oleg.placesnearme.presentation.viewdata.VenueViewData;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by Oleg on 10.08.2018.
 */
public class MapViewModel extends ViewModel {

    private final GetRecomendedVenuesInteractor interactor;
    private final MutableLiveData<Action<MapView>> observer = new MutableLiveData<>();

    private Disposable disposable;
    private LatLng currentLocation;

    public MapViewModel(@NonNull GetRecomendedVenuesInteractor interactor) {
        this.interactor = interactor;
        init();
    }

    public MutableLiveData<Action<MapView>> observe() {
        return observer;
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        Optional.of(disposable).ifPresent(Disposable::dispose);
    }

    private void init() {
        getRecommendedVenues();
    }

    private void getRecommendedVenues() {
        disposable = interactor.getRecommendedSection()
                .map(VenueViewData::mapFrom)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe(d -> observer.setValue(ViewActions.showLoading()))
                .subscribe(venues -> {
                            observer.setValue(ViewActions.hideLoading());
                            observer.setValue(ShowVenueDataAction.create(venues));
                        },
                        throwable -> {
                            observer.setValue(ViewActions.hideLoading());
                            observer.setValue(ViewActions.error(throwable));
                        });
    }

    public LatLng getCurrentLocation() {
        return currentLocation;
    }
}
