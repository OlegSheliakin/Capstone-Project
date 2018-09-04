package home.oleg.placesnearme.presentation.feature.map.marker;

import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import javax.inject.Inject;

import home.oleg.placesnearme.common.provider.MarkerIconProvider;
import home.oleg.placesnearme.presentation.viewdata.VenueViewData;
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
    public MarkerOptions mapFrom(@NonNull VenueViewData venueViewData) {
        LatLng latLng = new LatLng(venueViewData.getLat(), venueViewData.getLng());
        return new MarkerOptions()
                .icon(markerIconProvider.getIconByCategory(venueViewData.getSectionType()))
                .position(latLng);
    }

    @NonNull
    public List<MarkerOptions> mapFrom(@NonNull Collection<VenueViewData> venueViewDatas) {
        if(venueViewDatas.isEmpty()) {
            return Collections.emptyList();
        }

        List<MarkerOptions> markerOptions = new ArrayList<>();

        for (VenueViewData venueViewData : venueViewDatas) {
            markerOptions.add(mapFrom(venueViewData));
        }

        return markerOptions;
    }
}
