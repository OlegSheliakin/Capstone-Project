package home.oleg.placesnearme.feature_map.di

import dagger.BindsInstance
import dagger.Component
import home.oleg.placesnearme.coredata.di.RepoApi
import home.oleg.placesnearme.coredi.module.ViewModelFactoryModule
import home.oleg.placesnearme.coredi.scopes.FeatureScope
import home.oleg.placesnearme.corettools.di.ToolsApi
import home.oleg.placesnearme.feature_map.presentation.ui.VenuesMapFragment

@FeatureScope
@Component(
        dependencies = [ToolsApi::class, RepoApi::class],
        modules = [ViewModelFactoryModule::class, VenuesMapFragmentModule::class, ResourceModule::class])
interface PlacesMapFragmentComponent {

    fun inject(target: VenuesMapFragment)

    @Component.Builder
    interface Builder {
        fun toolsComponent(toolsProvider: ToolsApi): PlacesMapFragmentComponent.Builder

        fun repoComponent(repoApi: RepoApi): PlacesMapFragmentComponent.Builder

        @BindsInstance
        fun bind(mapFragment: VenuesMapFragment): PlacesMapFragmentComponent.Builder

        fun build(): PlacesMapFragmentComponent
    }

    object Injector {

        fun inject(fragment: VenuesMapFragment) {
            val toolsApi = ToolsApi.getInstance(fragment.activity!!.applicationContext)
            val repoApi = RepoApi.getInstance(fragment.activity!!.applicationContext)

            DaggerPlacesMapFragmentComponent.builder()
                    .toolsComponent(toolsApi)
                    .repoComponent(repoApi)
                    .bind(fragment)
                    .build()
                    .inject(fragment)
        }
    }

}
