package home.oleg.placesnearme.corenetwork.service

import home.oleg.placesnearme.corenetwork.config.NetworkConfig
import io.reactivex.schedulers.Schedulers
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

object FourSquareAPIFactory {

    private val CLIENT_ID_KEY = "client_id"
    private val CLIENT_SECRET_KEY = "client_secret"
    private val VERSION_KEY = "v"

    fun create(networkConfig: NetworkConfig): IFourSquareAPI {
        val logging = HttpLoggingInterceptor()

        val interceptor = createSecretInterceptor(networkConfig)

        logging.level = HttpLoggingInterceptor.Level.BODY

        val httpClient = OkHttpClient.Builder()
                .addInterceptor(logging)
                .addInterceptor(interceptor)
                .build()

        return Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.createWithScheduler(Schedulers.io()))
                .client(httpClient)
                .baseUrl(networkConfig.baseUrl())
                .build().create(IFourSquareAPI::class.java)
    }

    private fun createSecretInterceptor(networkConfig: NetworkConfig): Interceptor {
        return Interceptor { chain ->
            val url = chain.request().url().newBuilder()
                    .addQueryParameter(CLIENT_ID_KEY, networkConfig.clientId())
                    .addQueryParameter(CLIENT_SECRET_KEY, networkConfig.clientSecret())
                    .addQueryParameter(VERSION_KEY, networkConfig.apiVersion())
                    .build()
            val req = chain.request().newBuilder().url(url).build()

            chain.proceed(req)
        }
    }

}