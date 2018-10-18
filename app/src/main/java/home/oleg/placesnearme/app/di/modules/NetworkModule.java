package home.oleg.placesnearme.app.di.modules;

import android.support.annotation.NonNull;

import dagger.Module;
import dagger.Provides;
import home.oleg.placesnearme.BuildConfig;
import home.oleg.placesnearme.network.config.NetworkConfig;
import home.oleg.placesnearme.network.service.FourSquareAPIFactory;
import home.oleg.placesnearme.network.service.IFourSquareAPI;

@Module
public final class NetworkModule {

    @Provides
    @NonNull
    public IFourSquareAPI provideApi(NetworkConfig networkConfig) {
        return FourSquareAPIFactory.create(networkConfig);
    }

    @Provides
    @NonNull
    public NetworkConfig provideNetwowkConfig() {
        return new NetworkModule.MyNetworkConfig();
    }

    private static class MyNetworkConfig implements NetworkConfig {
        @Override
        public String baseUrl() {
            return BuildConfig.BASE_URL;
        }

        @Override
        public String clientSecret() {
            return BuildConfig.CLIENT_SECRET;
        }

        @Override
        public String clientId() {
            return BuildConfig.CLIENT_ID;
        }

        @Override
        public String apiVersion() {
            return BuildConfig.API_VERSION;
        }
    }
}
