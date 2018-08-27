package home.oleg.placesnearme.presentation.feature.map.viewmodel;

import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

import home.oleg.placenearme.interactors.GetVenuesInteractor;
import home.oleg.placesnearme.presentation.base.ShowVenueDataAction;
import home.oleg.placesnearme.presentation.base.ViewAction;
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

    private final GetVenuesInteractor interactor;

    private MutableLiveData<ViewAction<MapView>> observer = new MutableLiveData<>();
    private Disposable disposable;

    public MapViewModel(@NonNull GetVenuesInteractor interactor) {
        this.interactor = interactor;
        init();
    }

    public MutableLiveData<ViewAction<MapView>> observe() {
        return observer;
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

    private void getRecommendedVenues() {
        disposable = interactor.getRecommendedVenues()
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

}
