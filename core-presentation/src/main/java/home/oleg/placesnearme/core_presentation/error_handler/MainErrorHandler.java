package home.oleg.placesnearme.core_presentation.error_handler;

import com.smedialink.common.function.Function;

import java.io.IOException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;
import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;

import home.oleg.placesnearme.core_presentation.R;
import home.oleg.placesnearme.core_presentation.base.ErrorEvent;
import home.oleg.placesnearme.core_presentation.provider.ResourceProvider;
import retrofit2.HttpException;

/**
 * Created by Oleg Sheliakin on 27.10.2018.
 * Contact me by email - olegsheliakin@gmail.com
 */
public class MainErrorHandler implements ErrorHanlder {

    private final ResourceProvider resourceProvider;

    private final Map<Class<? extends Throwable>, Integer> mapIfStringRes = new HashMap<>();

    {
        mapIfStringRes.put(SocketTimeoutException.class, R.string.timeout_error);
        mapIfStringRes.put(IOException.class, R.string.no_internet_connection);
        mapIfStringRes.put(UnknownHostException.class, R.string.no_internet_connection);
    }

    @Inject
    public MainErrorHandler(ResourceProvider resourceProvider) {
        this.resourceProvider = resourceProvider;
    }

    @Override
    public ErrorEvent handle(Throwable throwable) {
        //todo add logger
        Integer stringRes = null;

        if(throwable instanceof HttpException) {
            HttpException httpException = (HttpException) throwable;
            switch(httpException.code()) {
                case 429:
                    stringRes = R.string.error_limit_exceeded;
                    break;
            }
        } else {
            stringRes = mapIfStringRes.get(throwable.getClass());
        }

        if(stringRes == null) {
            stringRes = R.string.unexpected_error;
        }


        String errorText = resourceProvider.getString(stringRes);

        return new ErrorEvent(throwable, errorText);
    }

}
