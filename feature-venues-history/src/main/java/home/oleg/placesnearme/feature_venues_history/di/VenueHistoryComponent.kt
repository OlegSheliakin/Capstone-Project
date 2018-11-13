package home.oleg.placesnearme.feature_venues_history.di

import dagger.BindsInstance
import dagger.Component
import home.oleg.placesnearme.AppApiProvider
import home.oleg.placesnearme.api.AppApi
import home.oleg.placesnearme.feature_venues_history.presentation.ui.VenuesHistoryFragment
import home.oleg.placesnearme.scopes.FeatureScope

@FeatureScope
@Component(dependencies = [AppApi::class], modules = [VenuesHistoryModule::class])
interface VenueHistoryComponent {
    fun inject(target: VenuesHistoryFragment)

    @Component.Builder
    interface Builder {
        fun appComponent(appApi: AppApi): VenueHistoryComponent.Builder

        @BindsInstance
        fun bind(target: VenuesHistoryFragment): VenueHistoryComponent.Builder

        fun build(): VenueHistoryComponent
    }

    object Injector {

        fun inject(fragment: VenuesHistoryFragment) {
            val appApi = AppApiProvider.Initializer.getAppApi(fragment)
            DaggerVenueHistoryComponent.builder()
                    .appComponent(appApi)
                    .bind(fragment)
                    .build()
                    .inject(fragment)
        }
    }
}
