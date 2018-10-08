package home.oleg.placesnearme.feature_map.view;

import home.oleg.placenearme.models.UserLocation;
import home.oleg.placesnearme.core_presentation.base.ErrorView;

/**
 * Created by Oleg Sheliakin on 17.09.2018.
 * Contact me by email - olegsheliakin@gmail.com
 */
public interface UserLocationView extends ErrorView {
    void show(UserLocation location);
}
