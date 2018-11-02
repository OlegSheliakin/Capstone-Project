package home.oleg.placesnearme.network.config

/**
 * Created by Oleg Sheliakin on 25.09.2018.
 * Contact me by email - olegsheliakin@gmail.com
 */
interface NetworkConfig {
    fun baseUrl(): String

    fun clientSecret(): String

    fun clientId(): String

    fun apiVersion(): String
}
