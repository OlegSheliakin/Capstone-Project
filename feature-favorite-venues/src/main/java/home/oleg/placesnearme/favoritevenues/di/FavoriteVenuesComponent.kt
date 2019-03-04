package home.oleg.placesnearme.favoritevenues.di

import dagger.BindsInstance
import dagger.Component
import home.oleg.placesnearme.coredata.di.RepoApi
import home.oleg.placesnearme.coredi.module.ViewModelFactoryModule
import home.oleg.placesnearme.coredi.scopes.FeatureScope
import home.oleg.placesnearme.corettools.di.ToolsApi
import home.oleg.placesnearme.favoritevenues.presentation.ui.FavoritePlacesFragment

@FeatureScope
@Component(
        dependencies = [ToolsApi::class, RepoApi::class],
        modules = [ViewModelFactoryModule::class, FavoriteVenuesFragmentModule::class])
interface FavoriteVenuesComponent {
    fun inject(target: FavoritePlacesFragment)

    @Component.Builder
    interface Builder {
        fun toolsComponent(toolsProvider: ToolsApi): FavoriteVenuesComponent.Builder

        fun repoComponent(repoApi: RepoApi): FavoriteVenuesComponent.Builder

        @BindsInstance
        fun bind(target: FavoritePlacesFragment): FavoriteVenuesComponent.Builder

        fun build(): FavoriteVenuesComponent
    }

    object Injector {

        fun inject(fragment: FavoritePlacesFragment) {
            val toolsProvider = ToolsApi.getInstance(fragment.activity!!.application)
            val repoApi = RepoApi.getInstance(fragment.activity!!.applicationContext)

            DaggerFavoriteVenuesComponent.builder()
                    .repoComponent(repoApi)
                    .toolsComponent(toolsProvider)
                    .bind(fragment)
                    .build()
                    .inject(fragment)
        }
    }
}
