package home.oleg.placesnearme.api;

import android.arch.lifecycle.ViewModelProvider;

import home.oleg.placenearme.interactors.EvaluateDistance;
import home.oleg.placenearme.interactors.GetRecommendedVenues;
import home.oleg.placesnearme.core_presentation.provider.ResourceProvider;
import home.oleg.placesnearme.network.config.NetworkConfig;
import home.oleg.placesnearme.network.service.IFourSquareAPI;

public interface AppApi {
    GetRecommendedVenues getRecommendedVenues();

    NetworkConfig getNetworkConfig();

    IFourSquareAPI getApi();

    ViewModelProvider.Factory getViewModelFactory();

    ResourceProvider getResourceProvider();

    EvaluateDistance getEvaluateDistance();
}
