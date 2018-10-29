package home.oleg.feature_widget;

import dagger.Component;
import home.oleg.placesnearme.api.AppApi;

@Component(dependencies = AppApi.class)
public interface PlacesWidgetComponent {
    void inject(PlaceRemoteService target);
}
