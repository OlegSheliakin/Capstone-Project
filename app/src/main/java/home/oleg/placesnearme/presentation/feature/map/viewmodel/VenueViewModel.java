package home.oleg.placesnearme.presentation.feature.map.viewmodel;

import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

import com.smedialink.common.Optional;
import com.smedialink.common.function.Action;

import home.oleg.placenearme.interactors.GetRecomendedVenuesInteractor;
import home.oleg.placenearme.interactors.GetUserLocationInteractor;
import home.oleg.placesnearme.presentation.base.ShowVenueDataAction;
import home.oleg.placesnearme.presentation.base.ViewActions;
import home.oleg.placesnearme.presentation.feature.map.view.MapView;
import home.oleg.placesnearme.presentation.viewdata.VenueViewData;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by Oleg on 10.08.2018.
 */
public final class VenueViewModel extends ViewModel {

    private final GetRecomendedVenuesInteractor interactor;
    private final GetUserLocationInteractor userLocationInteractor;

    private final MutableLiveData<Action<MapView>> observer = new MutableLiveData<>();

    private final CompositeDisposable compositeDisposable = new CompositeDisposable();

    public VenueViewModel(
            @NonNull GetRecomendedVenuesInteractor interactor,
            @NonNull GetUserLocationInteractor userLocationInteractor) {
        this.interactor = interactor;
        this.userLocationInteractor = userLocationInteractor;
    }

    public MutableLiveData<Action<MapView>> observer() {
        return observer;
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        compositeDisposable.clear();
    }

    public void getUserLocation() {
        compositeDisposable.add(userLocationInteractor.getUserLocation()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(location -> {
                            observer.setValue(mapView -> mapView.showUserLocation(location));
                        },
                        throwable -> {
                            observer.setValue(ViewActions.error(throwable));
                        })
        );
    }

    public void getRecommendedVenues() {
        compositeDisposable.add(interactor.getRecommendedSection()
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
                        })
        );
    }

}
