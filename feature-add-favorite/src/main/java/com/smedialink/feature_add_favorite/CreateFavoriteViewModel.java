package com.smedialink.feature_add_favorite;

import home.oleg.placenearme.interactors.CreateVenueFavorite;
import home.oleg.placesnearme.core_presentation.base.BaseViewModel;
import home.oleg.placesnearme.core_presentation.viewdata.VenueViewData;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class CreateFavoriteViewModel extends BaseViewModel<CreateFavoriteView> {

    private final CreateVenueFavorite addRemoveVenueFavorite;

    public CreateFavoriteViewModel(CreateVenueFavorite addRemoveVenueFavorite) {
        this.addRemoveVenueFavorite = addRemoveVenueFavorite;
    }

    public void manageFavorite(VenueViewData venueViewData) {
        addToDisposables(addRemoveVenueFavorite.execute(venueViewData.mapToDetailVenue())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(isFavorite -> {
                    if (isFavorite) {
                        setAction(CreateFavoriteView::favoriteAdded);
                    } else {
                        setAction(CreateFavoriteView::favoriteRemoved);
                    }
                }, Throwable::printStackTrace));
    }
}
