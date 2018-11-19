package home.oleg.placesnearme.coredi

import android.app.Activity
import android.app.Application
import androidx.fragment.app.Fragment
import home.oleg.placesnearme.coredi.api.AppApi

interface AppApiProvider {
    val appApi: AppApi

    object Initializer {
        
        fun getAppApi(activity: Activity): AppApi {
            val application = activity.application
            return getAppApiInternal(application)
        }

        fun getAppApi(fragment: Fragment): AppApi {
            val application = fragment.activity!!.application
            return getAppApiInternal(application)
        }

        private fun getAppApiInternal(application: Application): AppApi {
            return if (application is AppApiProvider) {
                (application as AppApiProvider).appApi
            } else {
                throw IllegalStateException("application is not an instance of AppApiProvider")
            }
        }
    }
}
