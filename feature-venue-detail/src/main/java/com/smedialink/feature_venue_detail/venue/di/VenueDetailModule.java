package com.smedialink.feature_venue_detail.venue.di;

import android.app.Activity;
import android.arch.lifecycle.LifecycleOwner;
import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;

import com.smedialink.feature_add_favorite.CreateFavoriteViewModel;
import com.smedialink.feature_venue_detail.venue.view.VenueFragment;
import com.smedialink.feature_venue_detail.venue.viewmodel.VenueViewModel;

import dagger.Module;
import dagger.Provides;
import home.oleg.feature_add_history.CheckInViewModel;
import io.reactivex.annotations.NonNull;

@Module
public final class VenueDetailModule {

    @Provides
    @NonNull
    static CheckInViewModel provideCheckInViewModel(
            VenueFragment fragment,
            ViewModelProvider.Factory factory) {
        return ViewModelProviders.of(fragment, factory).get(CheckInViewModel.class);
    }

    @Provides
    @NonNull
    static VenueViewModel provideVenueViewModel(
            VenueFragment fragment,
            ViewModelProvider.Factory factory) {
        return ViewModelProviders.of(fragment, factory).get(VenueViewModel.class);
    }

    @Provides
    @NonNull
    static CreateFavoriteViewModel provideCreateFavoriteViewModel(
            VenueFragment fragment,
            ViewModelProvider.Factory factory) {
        return ViewModelProviders.of(fragment, factory).get(CreateFavoriteViewModel.class);
    }

    @Provides
    @NonNull
    static Activity provideActivity(VenueFragment fragment) {
        return fragment.getActivity();
    }

    @Provides
    @NonNull
    static LifecycleOwner provideLifecycleOwner(VenueFragment fragment) {
        return fragment;
    }

}
