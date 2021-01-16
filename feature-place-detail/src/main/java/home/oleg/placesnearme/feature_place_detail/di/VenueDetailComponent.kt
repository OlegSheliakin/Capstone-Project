package home.oleg.placesnearme.feature_place_detail.di

import dagger.BindsInstance
import dagger.Component
import home.oleg.placesnearme.coredata.di.RepoApi
import home.oleg.placesnearme.coredi.module.ViewModelFactoryModule
import home.oleg.placesnearme.coredi.scopes.FeatureScope
import home.oleg.placesnearme.corettools.di.ToolsApi
import home.oleg.placesnearme.feature_place_detail.presentation.ui.VenueFragment

@FeatureScope
@Component(
        dependencies = [ToolsApi::class, RepoApi::class],
        modules = [ViewModelFactoryModule::class, VenueDetailModule::class])
interface VenueDetailComponent {
    fun inject(target: VenueFragment)

    @Component.Builder
    interface Builder {
        fun toolsComponent(toolsComponent: ToolsApi): Builder

        fun repoComponent(repoApi: RepoApi): Builder

        @BindsInstance
        fun bind(target: VenueFragment): Builder

        fun build(): VenueDetailComponent
    }

    object Injector {

        fun inject(fragment: VenueFragment) {
            val toolsProvider = ToolsApi.getInstance(fragment.activity!!.applicationContext)
            val repoApi = RepoApi.getInstance(fragment.activity!!.applicationContext)
            DaggerVenueDetailComponent.builder()
                    .toolsComponent(toolsProvider)
                    .repoComponent(repoApi)
                    .bind(fragment)
                    .build()
                    .inject(fragment)
        }
    }
}
