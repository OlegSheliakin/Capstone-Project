package home.oleg.placesnearme.feature_widget.di

import android.content.Context
import dagger.Component
import home.oleg.placesnearme.coredata.di.RepoApi
import home.oleg.placesnearme.feature_widget.presentation.ui.ListRemoteViewsFactory

@Component(dependencies = [RepoApi::class])
interface PlacesWidgetComponent {
    fun inject(target: ListRemoteViewsFactory)

    object Injector {
        fun inject(context: Context, target: ListRemoteViewsFactory) {
            val repoApi = RepoApi.getInstance(context)

            DaggerPlacesWidgetComponent
                    .builder()
                    .repoApi(repoApi)
                    .build().inject(target)
        }
    }

}
