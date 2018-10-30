package com.smedialink.feature_venue_detail.venue.viewmodel;

import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.support.annotation.NonNull;

import com.smedialink.common.Optional;
import com.smedialink.feature_venue_detail.state.VenueViewState;

import home.oleg.placenearme.interactors.EvaluateDistance;
import home.oleg.placenearme.interactors.GetDetailedVenue;
import home.oleg.placesnearme.core_presentation.base.ErrorEvent;
import home.oleg.placesnearme.core_presentation.error_handler.ErrorHanlder;
import home.oleg.placesnearme.core_presentation.viewdata.VenueViewData;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by Oleg Sheliakin on 18.09.2018.
 * Contact me by email - olegsheliakin@gmail.com
 */
public class VenueViewModel extends ViewModel {

    private final MutableLiveData<VenueViewState> state = new MutableLiveData<>();
    private final ErrorHanlder errorHanlder;
    private final GetDetailedVenue getDetailedVenue;
    private final EvaluateDistance evaluateDistance;

    private Disposable disposable;

    private VenueViewData venueViewData;

    public VenueViewModel(ErrorHanlder errorHanlder, GetDetailedVenue getDetailedVenue, EvaluateDistance evaluateDistance) {
        this.errorHanlder = errorHanlder;
        this.getDetailedVenue = getDetailedVenue;
        this.evaluateDistance = evaluateDistance;
    }

    public MutableLiveData<VenueViewState> getState() {
        return state;
    }

    public void setVenue(String venueId) {
        Optional.of(disposable).ifPresent(Disposable::dispose);

        disposable = getDetailedVenue.getDetailedVenue(venueId)
                .flatMapSingle(evaluateDistance::evaluateDistance)
                .map(VenueViewData::mapFrom)
                .doOnNext(data -> this.venueViewData = data)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe(disposable -> state.setValue(VenueViewState.loading()))
                .subscribe(
                        detailedVenue -> state.setValue(VenueViewState.success(detailedVenue)),
                        throwable -> {
                            ErrorEvent errorEvent = errorHanlder.handle(throwable);
                            state.setValue(VenueViewState.error(errorEvent));
                        });
    }

    public void load(String venueId) {
        Optional.of(disposable).ifPresent(Disposable::dispose);

        disposable = getDetailedVenue.getCachedDetailVenue(venueId)
                .flatMapSingle(evaluateDistance::evaluateDistance)
                .map(VenueViewData::mapFrom)
                .doOnNext(data -> this.venueViewData = data)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        detailedVenue -> state.setValue(VenueViewState.success(detailedVenue)),
                        throwable -> {
                            ErrorEvent errorEvent = errorHanlder.handle(throwable);
                            state.setValue(VenueViewState.error(errorEvent));
                        });
    }

    @NonNull
    public VenueViewData getVenueViewData() {
        return venueViewData;
    }

    public void cancel() {
        Optional.of(disposable).ifPresent(Disposable::dispose);
    }
}
