package home.oleg.placesnearme.app.di.modules

import androidx.annotation.NonNull
import dagger.Module
import dagger.Provides
import home.oleg.placesnearme.BuildConfig
import home.oleg.placesnearme.network.config.NetworkConfig
import home.oleg.placesnearme.network.service.FourSquareAPIFactory
import home.oleg.placesnearme.network.service.IFourSquareAPI

@Module
object NetworkModule {

    @JvmStatic
    @Provides
    internal fun provideApi(networkConfig: NetworkConfig): IFourSquareAPI {
        return FourSquareAPIFactory.create(networkConfig)
    }

    @JvmStatic
    @Provides
    internal fun provideNetworkConfig(): NetworkConfig {
        return NetworkModule.MyNetworkConfig()
    }

    private class MyNetworkConfig : NetworkConfig {
        override fun baseUrl(): String {
            return BuildConfig.BASE_URL
        }

        override fun clientSecret(): String {
            return BuildConfig.CLIENT_SECRET
        }

        override fun clientId(): String {
            return BuildConfig.CLIENT_ID
        }

        override fun apiVersion(): String {
            return BuildConfig.API_VERSION
        }
    }
}
