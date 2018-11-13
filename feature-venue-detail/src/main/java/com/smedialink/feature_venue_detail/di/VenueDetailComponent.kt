package com.smedialink.feature_venue_detail.di

import com.smedialink.feature_venue_detail.presentation.ui.VenueFragment

import dagger.BindsInstance
import dagger.Component
import home.oleg.placesnearme.AppApiProvider
import home.oleg.placesnearme.api.AppApi
import home.oleg.placesnearme.scopes.FeatureScope

@FeatureScope
@Component(dependencies = [AppApi::class], modules = [VenueDetailModule::class])
interface VenueDetailComponent {
    fun inject(target: VenueFragment)

    @Component.Builder
    interface Builder {
        fun appComponent(appApi: AppApi): VenueDetailComponent.Builder

        @BindsInstance
        fun bind(target: VenueFragment): VenueDetailComponent.Builder

        fun build(): VenueDetailComponent
    }

    object Injector {

        fun inject(fragment: VenueFragment) {
            val appApi = AppApiProvider.Initializer.getAppApi(fragment)
            DaggerVenueDetailComponent.builder()
                    .appComponent(appApi)
                    .bind(fragment)
                    .build()
                    .inject(fragment)
        }
    }
}
