package home.oleg.feature_widget.di

import dagger.Component
import home.oleg.feature_widget.presentation.PlaceRemoteService
import home.oleg.placesnearme.api.AppApi

@Component(dependencies = [AppApi::class])
interface PlacesWidgetComponent {
    fun inject(target: PlaceRemoteService)
}
