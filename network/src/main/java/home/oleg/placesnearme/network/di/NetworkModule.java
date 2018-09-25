package home.oleg.placesnearme.network.di;

import dagger.Module;
import dagger.Provides;
import home.oleg.placesnearme.network.config.NetworkConfig;
import home.oleg.placesnearme.network.service.FourSquareAPIFactory;
import home.oleg.placesnearme.network.service.IFourSquareAPI;
import io.reactivex.annotations.NonNull;

/**
 * Created by Oleg Sheliakin on 25.09.2018.
 * Contact me by email - olegsheliakin@gmail.com
 */
@Module
public final class NetworkModule {

    @Provides
    @NonNull
    public static IFourSquareAPI provideFourSquareApi(NetworkConfig config) {
        return FourSquareAPIFactory.create(config);
    }
}
