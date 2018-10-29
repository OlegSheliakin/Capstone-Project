package com.smedialink.feature_add_favorite;

import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

import home.oleg.placenearme.interactors.CreateVenueFavorite;
import home.oleg.placesnearme.core_presentation.base.MessageEvent;
import home.oleg.placesnearme.core_presentation.provider.ResourceProvider;
import home.oleg.placesnearme.core_presentation.viewdata.VenueViewData;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class CreateFavoriteViewModel extends ViewModel {

    private final CreateVenueFavorite addRemoveVenueFavorite;
    private final MutableLiveData<MessageEvent> state = new MutableLiveData<>();
    private final ResourceProvider resourceProvider;

    private Disposable disposable;

    public CreateFavoriteViewModel(CreateVenueFavorite addRemoveVenueFavorite,
                                   ResourceProvider resourceProvider) {
        this.addRemoveVenueFavorite = addRemoveVenueFavorite;
        this.resourceProvider = resourceProvider;
    }

    public MutableLiveData<MessageEvent> getState() {
        return state;
    }

    public void manageFavorite(VenueViewData venueViewData) {
        if (venueViewData == null) return;

        disposable = addRemoveVenueFavorite.execute(venueViewData.mapToDetailVenue())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(isFavorite -> {
                    int stringsRes;
                    if (isFavorite) {
                        stringsRes = R.string.add_favorite_message_success_added;
                    } else {
                        stringsRes = R.string.add_favorite_message_success_removed;
                    }
                    MessageEvent messageEvent = new MessageEvent(resourceProvider.getString(stringsRes));
                    state.setValue(messageEvent);
                }, Throwable::printStackTrace);
    }
}
