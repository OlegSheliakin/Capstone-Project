package home.oleg.placesnearme.core_network.service;

import home.oleg.placesnearme.core_network.config.NetworkConfig;

import io.reactivex.schedulers.Schedulers;
import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public final class FourSquareAPIFactory {

    private static final String CLIENT_ID_KEY = "client_id";
    private static final String CLIENT_SECRET_KEY = "client_secret";
    private static final String VERSION_KEY = "v";

    private FourSquareAPIFactory() {
    }

    public static IFourSquareAPI create(NetworkConfig networkConfig) {
        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();

        Interceptor interceptor = createSecretInterceptor(networkConfig);

        logging.setLevel(HttpLoggingInterceptor.Level.BODY);

        OkHttpClient httpClient = new OkHttpClient
                .Builder()
                .addInterceptor(logging)
                .addInterceptor(interceptor)
                .build();

        return new Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.createWithScheduler(Schedulers.io()))
                .client(httpClient)
                .baseUrl(networkConfig.baseUrl())
                .build().create(IFourSquareAPI.class);
    }

    private static Interceptor createSecretInterceptor(NetworkConfig networkConfig) {
        return chain -> {
            HttpUrl url = chain.request().url().newBuilder()
                    .addQueryParameter(CLIENT_ID_KEY, networkConfig.clientId())
                    .addQueryParameter(CLIENT_SECRET_KEY, networkConfig.clientSecret())
                    .addQueryParameter(VERSION_KEY, networkConfig.apiVersion())
                    .build();
            Request req = chain.request().newBuilder().url(url).build();

            return chain.proceed(req);
        };
    }

}