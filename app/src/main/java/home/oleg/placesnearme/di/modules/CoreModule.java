package home.oleg.placesnearme.di.modules;

import dagger.Module;
import dagger.Provides;
import home.oleg.placesnearme.presentation.errorhandler.ErrorHandler;
import home.oleg.placesnearme.repositories.QueryParamCreator;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.CompositeDisposable;

/**
 * Created by Oleg Sheliakin on 14.08.2018.
 * Contact me by email - olegsheliakin@gmail.com
 */

@Module
public class CoreModule {

    @Provides
    @NonNull
    static CompositeDisposable provideCompositeDisposable() {
        return new CompositeDisposable();
    }

    @Provides
    @NonNull
    static QueryParamCreator provideQueryParamCreator() {
        return new QueryParamCreator();
    }

    //TODO implement error handler
    @Provides
    @NonNull
    static ErrorHandler provideErrorHandler() {
        return new ErrorHandler() {
            @Override
            public void handle(Throwable throwable) {
                throwable.printStackTrace();
            }
        };
    }
}
