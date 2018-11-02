package com.smedialink.feature_venue_detail.venue.di

import android.app.Activity
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.smedialink.feature_add_favorite.CreateFavoriteViewModel
import com.smedialink.feature_venue_detail.venue.view.VenueFragment
import com.smedialink.feature_venue_detail.venue.viewmodel.VenueViewModel
import dagger.Module
import dagger.Provides
import home.oleg.feature_add_history.CheckInViewModel

@Module
class VenueDetailModule {

    @Provides
    fun provideCheckInViewModel(
            fragment: VenueFragment,
            factory: ViewModelProvider.Factory): CheckInViewModel {
        return ViewModelProviders.of(fragment, factory).get(CheckInViewModel::class.java)
    }

    @Provides
    fun provideVenueViewModel(
            fragment: VenueFragment,
            factory: ViewModelProvider.Factory): VenueViewModel {
        return ViewModelProviders.of(fragment, factory).get(VenueViewModel::class.java)
    }

    @Provides
    fun provideCreateFavoriteViewModel(
            fragment: VenueFragment,
            factory: ViewModelProvider.Factory): CreateFavoriteViewModel {
        return ViewModelProviders.of(fragment, factory).get(CreateFavoriteViewModel::class.java)
    }

    @Provides
    fun provideActivity(fragment: VenueFragment): Activity {
        return fragment.activity!!
    }

    @Provides
    fun provideLifecycleOwner(fragment: VenueFragment): LifecycleOwner {
        return fragment
    }

}
