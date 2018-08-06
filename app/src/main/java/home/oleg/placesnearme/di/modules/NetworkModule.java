package home.oleg.placesnearme.di.modules;

import dagger.Module;
import dagger.Provides;
import home.oleg.placesnearme.data.service.FourSquareAPIFactory;
import home.oleg.placesnearme.data.service.IFourSquareAPI;
import io.reactivex.annotations.NonNull;

@Module
public final class NetworkModule {

    @Provides
    @NonNull
    public IFourSquareAPI provide() {
        return FourSquareAPIFactory.create();
    }

}
