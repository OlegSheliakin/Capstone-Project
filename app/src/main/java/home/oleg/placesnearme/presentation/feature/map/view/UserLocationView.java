package home.oleg.placesnearme.presentation.feature.map.view;

import home.oleg.placenearme.models.UserLocation;
import home.oleg.placesnearme.presentation.base.ErrorView;
import home.oleg.placesnearme.presentation.base.View;
import io.reactivex.annotations.NonNull;

/**
 * Created by Oleg Sheliakin on 17.09.2018.
 * Contact me by email - olegsheliakin@gmail.com
 */
public interface UserLocationView extends ErrorView {
    void showUserLocation(@NonNull UserLocation userLocation);
}
