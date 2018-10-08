package home.oleg.placesnearme.feature_map.view_action;

import android.support.annotation.NonNull;

import com.smedialink.common.Optional;
import com.smedialink.common.function.Action;

import home.oleg.placenearme.models.UserLocation;
import home.oleg.placesnearme.feature_map.view.UserLocationView;

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
        Optional.of(userLocation).ifPresent(userLocationView::show);
    }
}
