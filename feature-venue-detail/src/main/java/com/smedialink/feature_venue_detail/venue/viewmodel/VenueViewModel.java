package com.smedialink.feature_venue_detail.venue.viewmodel;

import android.support.annotation.NonNull;

import com.smedialink.common.Optional;
import com.smedialink.feature_venue_detail.venue.view.VenueView;

import home.oleg.placenearme.interactors.GetDetailedVenue;
import home.oleg.placesnearme.core_presentation.base.BaseViewModel;
import home.oleg.placesnearme.core_presentation.base.ErrorView;
import home.oleg.placesnearme.core_presentation.base.LoadingView;
import home.oleg.placesnearme.core_presentation.viewdata.PreviewVenueViewData;
import home.oleg.placesnearme.core_presentation.viewdata.VenueViewData;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by Oleg Sheliakin on 18.09.2018.
 * Contact me by email - olegsheliakin@gmail.com
 */
public class VenueViewModel extends BaseViewModel<VenueView> {

    private final GetDetailedVenue getDetailedVenue;

    private Disposable disposable;

    private VenueViewData venueViewData;

    public VenueViewModel(GetDetailedVenue getDetailedVenue) {
        this.getDetailedVenue = getDetailedVenue;
    }

    public void setVenue(String venueId) {
        Optional.of(disposable).ifPresent(Disposable::dispose);

        disposable = getDetailedVenue.getDetailedVenue(venueId)
                .map(VenueViewData::mapFrom)
                .doOnNext(data -> this.venueViewData = data)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe(disposable -> setState(LoadingView::showLoading))
                .subscribe(
                        detailedVenue -> setState(venueView -> {
                            venueView.hideLoading();
                            venueView.show(detailedVenue);
                        }),
                        throwable -> {
                            throwable.printStackTrace();
                            setState(venueView -> {
                                venueView.hideLoading();
                                venueView.showError();
                            });
                        });
    }

    public void load(String venueId) {
        Optional.of(disposable).ifPresent(Disposable::dispose);

        disposable = getDetailedVenue.getCachedDetailVenue(venueId)
                .map(VenueViewData::mapFrom)
                .doOnNext(data -> this.venueViewData = data)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        detailedVenue -> setState(venueView -> {
                            venueView.show(detailedVenue);
                        }),
                        throwable -> {
                            setState(ErrorView::showError);
                        });
    }

    @NonNull
    public VenueViewData getVenueViewData() {
        return venueViewData;
    }
}
