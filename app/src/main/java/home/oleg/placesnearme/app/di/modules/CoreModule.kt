package home.oleg.placesnearme.app.di.modules

import android.content.Context
import android.content.SharedPreferences
import dagger.Module
import dagger.Provides
import home.oleg.placesnearme.BuildConfig
import home.oleg.placesnearme.app.PlacesNearMeApp
import home.oleg.placesnearme.corenetwork.config.NetworkConfig

/**
 * Created by Oleg Sheliakin on 14.08.2018.
 * Contact me by email - olegsheliakin@gmail.com
 */

@Module
object CoreModule {

    @JvmStatic
    @Provides
    internal fun provideSharedPreferences(app: PlacesNearMeApp): SharedPreferences {
        return app.getSharedPreferences(PlacesNearMeApp::class.java.simpleName, Context.MODE_PRIVATE)
    }

    @JvmStatic
    @Provides
    internal fun provideContext(app: PlacesNearMeApp): Context = app

    @JvmStatic
    @Provides
    internal fun provideNetworkConfig(): NetworkConfig {
        return MyNetworkConfig()
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
