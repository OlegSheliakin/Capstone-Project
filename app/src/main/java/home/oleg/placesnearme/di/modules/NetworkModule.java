package home.oleg.placesnearme.di.modules;

import dagger.Module;
import dagger.Provides;
import home.oleg.placesnearme.BuildConfig;
import home.oleg.placesnearme.core_network.config.NetworkConfig;
import home.oleg.placesnearme.core_network.service.FourSquareAPIFactory;
import home.oleg.placesnearme.core_network.service.IFourSquareAPI;
import io.reactivex.annotations.NonNull;

@Module
public final class NetworkModule {

    @Provides
    @NonNull
    public NetworkConfig provideNetwowkConfig() {
        return new NetworkModule.MyNetworkConfig();
    }

    @Provides
    @NonNull
    public IFourSquareAPI provide(NetworkConfig networkConfig) {
        return FourSquareAPIFactory.create(networkConfig);
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
