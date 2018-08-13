package home.oleg.placesnearme.data.service;

import home.oleg.placesnearme.data.BuildConfig;
import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class FourSquareAPIFactory {

    private static final String BASE_URL = BuildConfig.BASE_URL;
    private static final String CLIENT_ID_KEY = "client_id";
    private static final String CLIENT_SECRET_KEY = "client_secret";
    private static final String VERSION_KEY = "v";

    public static IFourSquareAPI create() {
        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();

        Interceptor interceptor = createSecretInterceptor();

        logging.setLevel(HttpLoggingInterceptor.Level.BODY);

        OkHttpClient httpClient = new OkHttpClient
                .Builder()
                .addInterceptor(logging)
                .addInterceptor(interceptor)
                .build();

        return new Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .client(httpClient)
                .baseUrl(BASE_URL)
                .build().create(IFourSquareAPI.class);
    }

    private static Interceptor createSecretInterceptor() {
        return chain -> {
            HttpUrl url = chain.request().url().newBuilder()
                    .addQueryParameter(CLIENT_ID_KEY, BuildConfig.CLIENT_ID)
                    .addQueryParameter(CLIENT_SECRET_KEY, BuildConfig.CLIENT_SECRET)
                    .addQueryParameter(VERSION_KEY, BuildConfig.API_VERSION)
                    .build();
            Request req = chain.request().newBuilder().url(url).build();

            return chain.proceed(req);
        };
    }

}