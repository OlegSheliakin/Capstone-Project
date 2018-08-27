package home.oleg.placesnearme.presentation.feature.map;

import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import home.oleg.placesnearme.presentation.viewdata.VenueViewData;
import io.reactivex.annotations.NonNull;

public class MarkerMapper {

    private final MarkerIconProvider markerIconProvider;

    @Inject
    public MarkerMapper(@NonNull MarkerIconProvider markerIconProvider) {
        this.markerIconProvider = markerIconProvider;
    }

    @NonNull
    public MarkerOptions mapFrom(@NonNull VenueViewData venueViewData) {
        LatLng latLng = new LatLng(venueViewData.getLat(), venueViewData.getLng());
        return new MarkerOptions()
                .title(venueViewData.getTitle())
                .icon(markerIconProvider.getIconByCategory(venueViewData.getCategory()))
                .position(latLng);
    }

    @NonNull
    public List<MarkerOptions> mapFrom(@NonNull Iterable<VenueViewData> venueViewDatas) {
        List<MarkerOptions> markerOptions = new ArrayList<>();

        for (VenueViewData venueViewData : venueViewDatas) {
            markerOptions.add(mapFrom(venueViewData));
        }

        return markerOptions;
    }
}
