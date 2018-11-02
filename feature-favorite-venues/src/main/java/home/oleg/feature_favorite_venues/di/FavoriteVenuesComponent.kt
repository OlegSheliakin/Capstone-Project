package home.oleg.feature_favorite_venues.di

import dagger.BindsInstance
import dagger.Component
import home.oleg.feature_favorite_venues.FavoritePlacesFragment
import home.oleg.placesnearme.AppApiProvider
import home.oleg.placesnearme.api.AppApi
import home.oleg.placesnearme.scopes.FeatureScope

@FeatureScope
@Component(dependencies = [AppApi::class], modules = [FavoriteVenuesFragmentModule::class])
interface FavoriteVenuesComponent {
    fun inject(target: FavoritePlacesFragment)

    @Component.Builder
    interface Builder {
        fun appComponent(appApi: AppApi): FavoriteVenuesComponent.Builder

        @BindsInstance
        fun bind(target: FavoritePlacesFragment): FavoriteVenuesComponent.Builder

        fun build(): FavoriteVenuesComponent
    }

    object Injector {

        fun inject(fragment: FavoritePlacesFragment) {
            val appApi = AppApiProvider.Initializer.getAppApi(fragment)
            DaggerFavoriteVenuesComponent.builder()
                    .appComponent(appApi)
                    .bind(fragment)
                    .build()
                    .inject(fragment)
        }
    }
}
