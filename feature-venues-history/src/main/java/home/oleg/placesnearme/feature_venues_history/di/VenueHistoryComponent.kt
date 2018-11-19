package home.oleg.placesnearme.feature_venues_history.di

import dagger.BindsInstance
import dagger.Component
import home.oleg.placesnearme.coredata.di.RepoApi
import home.oleg.placesnearme.coredi.module.ViewModelFactoryModule
import home.oleg.placesnearme.coredi.scopes.FeatureScope
import home.oleg.placesnearme.corettools.di.ToolsApi
import home.oleg.placesnearme.feature_venues_history.presentation.ui.VenuesHistoryFragment

@FeatureScope
@Component(
        dependencies = [ToolsApi::class, RepoApi::class],
        modules = [ViewModelFactoryModule::class, VenuesHistoryModule::class])
interface VenueHistoryComponent {
    fun inject(target: VenuesHistoryFragment)

    @Component.Builder
    interface Builder {
        fun toolsProvider(toolsProvider: ToolsApi): VenueHistoryComponent.Builder
        fun repoComponent(repoApi: RepoApi): VenueHistoryComponent.Builder

        @BindsInstance
        fun bind(target: VenuesHistoryFragment): VenueHistoryComponent.Builder

        fun build(): VenueHistoryComponent
    }

    object Injector {

        fun inject(fragment: VenuesHistoryFragment) {
            val toolsProvider = ToolsApi.getInstance(fragment.activity!!.applicationContext)
            val repoApi = RepoApi.getInstance(fragment.activity!!.applicationContext)

            DaggerVenueHistoryComponent.builder()
                    .toolsProvider(toolsProvider)
                    .repoComponent(repoApi)
                    .bind(fragment)
                    .build()
                    .inject(fragment)
        }
    }
}
