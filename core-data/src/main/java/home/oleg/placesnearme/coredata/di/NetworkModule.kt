package home.oleg.placesnearme.coredata.di

import dagger.Module
import dagger.Provides
import home.oleg.placesnearme.corenetwork.config.NetworkConfig
import home.oleg.placesnearme.corenetwork.service.FourSquareAPIFactory
import home.oleg.placesnearme.corenetwork.service.IFourSquareAPI

@Module
object NetworkModule {

    @JvmStatic
    @Provides
    internal fun provideApi(networkConfig: NetworkConfig): IFourSquareAPI {
        return FourSquareAPIFactory.create(networkConfig)
    }

}
