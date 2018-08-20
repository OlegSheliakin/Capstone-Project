package home.oleg.placesnearme.presentation.mapper;

import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;
import java.util.List;

import home.oleg.placesnearme.presentation.viewobjects.VenueViewObject;
import io.reactivex.annotations.NonNull;

public class MarkerMapper {

    @NonNull
    public static MarkerOptions mapFrom(@NonNull VenueViewObject venueViewObject) {
        LatLng latLng = new LatLng(venueViewObject.getLat(), venueViewObject.getLng());
        return new MarkerOptions()
                .title(venueViewObject.getTitle())
                .position(latLng);
    }

    @NonNull
    public static List<MarkerOptions> mapFrom(@NonNull Iterable<VenueViewObject> venueViewObjects) {
        List<MarkerOptions> markerOptions = new ArrayList<>();

        for (VenueViewObject venueViewObject : venueViewObjects) {
            markerOptions.add(mapFrom(venueViewObject));
        }

        return markerOptions;
    }

}
