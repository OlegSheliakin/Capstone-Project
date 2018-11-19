package home.oleg.placesnearme.coredi.api

import android.content.Context
import android.content.SharedPreferences
import home.oleg.placesnearme.corenetwork.config.NetworkConfig

interface AppApi {

    val networkConfig: NetworkConfig

    val prefs: SharedPreferences

    val applicationContext: Context
}
