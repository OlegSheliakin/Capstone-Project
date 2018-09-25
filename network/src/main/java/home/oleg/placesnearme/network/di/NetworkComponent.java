package home.oleg.placesnearme.network.di;

import dagger.BindsInstance;
import dagger.Component;
import home.oleg.placesnearme.network.config.NetworkConfig;
import home.oleg.placesnearme.network.service.IFourSquareAPI;

/**
 * Created by Oleg Sheliakin on 25.09.2018.
 * Contact me by email - olegsheliakin@gmail.com
 */
@Component(modules = NetworkModule.class)
public interface NetworkComponent {

    IFourSquareAPI getApi();
    
    @Component.Builder
    interface Builder {
        @BindsInstance
        NetworkComponent.Builder networkConfig(NetworkConfig networkConfig);

        NetworkComponent build();
    }
}
