package home.oleg.placesnearme;

import android.app.Activity;
import android.app.Application;
import android.support.v4.app.Fragment;

import home.oleg.placesnearme.api.AppApi;

public interface AppApiProvider {
    AppApi getAppApi();

    class Initializer {
        public static AppApi getAppApi(Activity activity) {
            Application application = activity.getApplication();
            return getAppApiInternal(application);
        }

        public static AppApi getAppApi(Fragment fragment) {
            Application application = fragment.getActivity().getApplication();
            return getAppApiInternal(application);
        }

        private static AppApi getAppApiInternal(Application application) {
            if (application instanceof AppApiProvider) {
                return ((AppApiProvider) application).getAppApi();
            } else {
                throw new IllegalStateException("application is not an instance of AppApiProvider");
            }
        }
    }
}
