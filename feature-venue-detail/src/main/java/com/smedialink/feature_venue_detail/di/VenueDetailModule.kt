package com.smedialink.feature_venue_detail.di

import android.app.Activity
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.smedialink.feature_venue_detail.presentation.ui.VenueFragment
import com.smedialink.feature_venue_detail.presentation.VenueViewModel
import dagger.Module
import dagger.Provides

@Module
class VenueDetailModule {

    @Provides
    fun provideVenueViewModel(
            fragment: VenueFragment,
            factory: ViewModelProvider.Factory): VenueViewModel {
        return ViewModelProviders.of(fragment, factory).get(VenueViewModel::class.java)
    }

    @Provides
    fun provideActivity(fragment: VenueFragment): Activity {
        return fragment.activity!!
    }

    @Provides
    fun provideLifecycleOwner(fragment: VenueFragment): LifecycleOwner = fragment

}
