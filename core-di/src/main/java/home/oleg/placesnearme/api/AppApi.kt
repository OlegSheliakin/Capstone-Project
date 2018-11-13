package home.oleg.placesnearme.api

import androidx.lifecycle.ViewModelProvider
import com.home.olegsheliakin.corettools.resource.ResourceProvider
import home.oleg.placenearme.interactors.EvaluateDistance
import home.oleg.placenearme.interactors.GetRecommendedVenues
import home.oleg.placesnearme.network.config.NetworkConfig
import home.oleg.placesnearme.network.service.IFourSquareAPI

interface AppApi {
    val recommendedVenues: GetRecommendedVenues

    val networkConfig: NetworkConfig

    val api: IFourSquareAPI

    val viewModelFactory: ViewModelProvider.Factory

    val resourceProvider: ResourceProvider

    val evaluateDistance: EvaluateDistance
}
