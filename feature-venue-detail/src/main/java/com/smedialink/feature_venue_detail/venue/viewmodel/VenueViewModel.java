package com.smedialink.feature_venue_detail.venue.viewmodel;

import com.smedialink.feature_venue_detail.venue.view.VenueView;

import home.oleg.placenearme.interactors.GetDetailedVenue;
import home.oleg.placesnearme.core_presentation.base.BaseViewModel;
import home.oleg.placesnearme.core_presentation.view_actions.ViewActions;
import home.oleg.placesnearme.core_presentation.viewdata.VenueMapViewData;
import home.oleg.placesnearme.core_presentation.viewdata.VenueViewData;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by Oleg Sheliakin on 18.09.2018.
 * Contact me by email - olegsheliakin@gmail.com
 */
public class VenueViewModel extends BaseViewModel<VenueView> {

    private final GetDetailedVenue getDetailedVenue;

    private VenueMapViewData venue;

    public VenueViewModel(GetDetailedVenue getDetailedVenue) {
        this.getDetailedVenue = getDetailedVenue;
    }

    public void setVenue(VenueMapViewData venue) {
        this.venue = venue;

        addToDisposables(getDetailedVenue.getDetailedVenue(venue.getId())
                .map(VenueViewData::mapFrom)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe(disposable -> setAction(ShowVenueMapAction.create(venue)))
                .subscribe(
                        detailedVenue -> setAction(ShowDetailedVenueAction.create(detailedVenue)),
                        throwable -> setAction(ViewActions.error(throwable))
                ));
    }

}
