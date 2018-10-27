package home.oleg.placesnearme.api;

import android.arch.lifecycle.ViewModelProvider;

import home.oleg.placesnearme.core_presentation.error_handler.ErrorHanlder;
import home.oleg.placesnearme.core_presentation.provider.ResourceProvider;
import home.oleg.placesnearme.network.config.NetworkConfig;
import home.oleg.placesnearme.network.service.IFourSquareAPI;

public interface AppApi {
    NetworkConfig getNetworkConfig();

    IFourSquareAPI getApi();

    ViewModelProvider.Factory getViewModelFactory();

    ResourceProvider getResourceProvider();
}
