package home.oleg.placesnearme.feature_map.di

import dagger.BindsInstance
import dagger.Component
import home.oleg.placesnearme.coredata.di.RepoApi
import home.oleg.placesnearme.coredi.module.ViewModelFactoryModule
import home.oleg.placesnearme.coredi.scopes.FeatureScope
import home.oleg.placesnearme.corettools.di.ToolsApi
import home.oleg.placesnearme.feature_map.presentation.MapFragment

@FeatureScope
@Component(
        dependencies = [ToolsApi::class, RepoApi::class],
        modules = [ViewModelFactoryModule::class, MapFragmentModule::class, ResourceModule::class])
interface MapFragmentComponent {

    fun inject(target: MapFragment)

    @Component.Builder
    interface Builder {
        fun toolsComponent(toolsProvider: ToolsApi): MapFragmentComponent.Builder

        fun repoComponent(repoApi: RepoApi): MapFragmentComponent.Builder

        @BindsInstance
        fun bind(mapFragment: MapFragment): MapFragmentComponent.Builder

        fun build(): MapFragmentComponent
    }

    object Injector {

        fun inject(fragment: MapFragment) {
            val toolsApi = ToolsApi.getInstance(fragment.activity!!.applicationContext)
            val repoApi = RepoApi.getInstance(fragment.activity!!.applicationContext)

            DaggerMapFragmentComponent.builder()
                    .toolsComponent(toolsApi)
                    .repoComponent(repoApi)
                    .bind(fragment)
                    .build()
                    .inject(fragment)
        }
    }

}
