package home.oleg.placesnearme.feature_map.marker;

import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.smedialink.common.Pair;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import javax.inject.Inject;

import home.oleg.placesnearme.core_presentation.viewdata.VenueMapViewData;
import home.oleg.placesnearme.core_presentation.viewdata.VenueViewData;
import io.reactivex.annotations.NonNull;

/**
 * Created by Oleg Sheliakin on 14.08.2018.
 * Contact me by email - olegsheliakin@gmail.com
 */
public class MarkerMapper {

    private final MarkerIconProvider markerIconProvider;

    @Inject
    public MarkerMapper(@NonNull MarkerIconProvider markerIconProvider) {
        this.markerIconProvider = markerIconProvider;
    }

    @NonNull
    public Pair<MarkerOptions, VenueMapViewData> mapFrom(@NonNull VenueMapViewData venueViewData) {
        LatLng latLng = new LatLng(venueViewData.getLat(), venueViewData.getLng());
        return new Pair<>(new MarkerOptions()
                .icon(markerIconProvider.getIconByCategory(venueViewData.getSectionType()))
                .position(latLng), venueViewData);
    }

    @NonNull
    public List<Pair<MarkerOptions, VenueMapViewData>> mapFrom(@NonNull Collection<VenueMapViewData> venueViewDatas) {
        if(venueViewDatas.isEmpty()) {
            return Collections.emptyList();
        }

        List<Pair<MarkerOptions, VenueMapViewData>> markerOptions = new ArrayList<>();

        for (VenueMapViewData venueViewData : venueViewDatas) {
            markerOptions.add(mapFrom(venueViewData));
        }

        return markerOptions;
    }
}
