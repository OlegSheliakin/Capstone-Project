package home.oleg.placesnearme.presentation.view_action;

import android.support.annotation.NonNull;

import com.smedialink.common.function.Action;

import home.oleg.placenearme.models.UserLocation;
import home.oleg.placesnearme.presentation.feature.map.view.UserLocationView;

/**
 * Created by Oleg Sheliakin on 18.09.2018.
 * Contact me by email - olegsheliakin@gmail.com
 */
public final class ShowUserLocationAction<T extends UserLocationView> implements Action<T> {

    private final UserLocation userLocation;

    private ShowUserLocationAction(UserLocation userLocation) {
        this.userLocation = userLocation;
    }

    public static <VIEW extends UserLocationView> ShowUserLocationAction<VIEW> create(UserLocation userLocation) {
        return new ShowUserLocationAction<>(userLocation);
    }

    @Override
    public void perform(@NonNull UserLocationView userLocationView) {
        userLocationView.showUserLocation(userLocation);
    }
}
