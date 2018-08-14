package home.oleg.placesnearme.di.modules;

import dagger.Module;
import dagger.Provides;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.CompositeDisposable;

/**
 * Created by Oleg Sheliakin on 14.08.2018.
 * Contact me by email - olegsheliakin@gmail.com
 */

@Module
public final class CoreModule {

    @Provides
    @NonNull
    static CompositeDisposable provideCompositeDisposable() {
        return new CompositeDisposable();
    }
}
