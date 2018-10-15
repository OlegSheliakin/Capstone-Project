package com.smedialink.feature_venue_detail.venue.viewmodel;

import com.smedialink.feature_venue_detail.venue.view.VenueView;

import home.oleg.placenearme.interactors.AddRemoveVenueFavorite;
import home.oleg.placenearme.interactors.GetDetailedVenue;
import home.oleg.placenearme.models.DetailedVenue;
import home.oleg.placesnearme.core_presentation.base.BaseViewModel;
import home.oleg.placesnearme.core_presentation.base.ErrorView;
import home.oleg.placesnearme.core_presentation.viewdata.ShortVenueViewData;
import home.oleg.placesnearme.core_presentation.viewdata.VenueViewData;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by Oleg Sheliakin on 18.09.2018.
 * Contact me by email - olegsheliakin@gmail.com
 */
public class VenueViewModel extends BaseViewModel<VenueView> {

    private final GetDetailedVenue getDetailedVenue;
    private final AddRemoveVenueFavorite addRemoveVenueFavorite;

    private DetailedVenue detailedVenue;

    public VenueViewModel(GetDetailedVenue getDetailedVenue,
                          AddRemoveVenueFavorite addRemoveVenueFavorite) {
        this.getDetailedVenue = getDetailedVenue;
        this.addRemoveVenueFavorite = addRemoveVenueFavorite;
    }

    public void setVenue(ShortVenueViewData venue) {
        addToDisposables(getDetailedVenue.getDetailedVenue(venue.getId())
                .doOnSuccess(detailedVenue -> VenueViewModel.this.detailedVenue = detailedVenue)
                .map(VenueViewData::mapFrom)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe(disposable -> {
                    setAction(venueView -> {
                        venueView.showLoading();
                        venueView.showShortVenue(venue);
                    });
                })
                .subscribe(
                        detailedVenue -> {
                            setAction(venueView -> {
                                venueView.hideLoading();
                                venueView.show(detailedVenue);
                            });
                        },
                        throwable -> {
                            setAction(venueView -> {
                                venueView.hideLoading();
                                venueView.showError();
                            });
                        }
                ));
    }

    public void favoriteClicked() {
        if(detailedVenue == null) {
            return;
        }

        addToDisposables(addRemoveVenueFavorite.execute(detailedVenue)
                .doOnSuccess(detailedVenue -> VenueViewModel.this.detailedVenue = detailedVenue)
                .map(VenueViewData::mapFrom)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        detailedVenue -> setAction(venueView -> venueView.show(detailedVenue)),
                        throwable -> setAction(ErrorView::showError)
                ));
    }
}
