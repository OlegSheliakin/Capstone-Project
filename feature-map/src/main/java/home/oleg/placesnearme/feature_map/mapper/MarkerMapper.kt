package home.oleg.placesnearme.feature_map.mapper

import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.smedialink.common.Pair

import java.util.ArrayList
import java.util.Collections

import javax.inject.Inject

import home.oleg.placesnearme.core_presentation.viewdata.PreviewVenueViewData
import io.reactivex.annotations.NonNull

/**
 * Created by Oleg Sheliakin on 14.08.2018.
 * Contact me by email - olegsheliakin@gmail.com
 */
class MarkerMapper @Inject constructor(private val markerIconProvider: MarkerIconProvider) {

    fun mapFrom(venueViewData: PreviewVenueViewData): Pair<MarkerOptions, PreviewVenueViewData> {
        val latLng = LatLng(venueViewData.lat, venueViewData.lng)
        return Pair(MarkerOptions()
                .icon(markerIconProvider.getIconByCategory(venueViewData.sectionType))
                .position(latLng), venueViewData)
    }

    fun mapFrom(venueViewDatas: Collection<PreviewVenueViewData>): List<Pair<MarkerOptions, PreviewVenueViewData>> {
        if (venueViewDatas.isEmpty()) {
            return emptyList()
        }

        return venueViewDatas.map(this::mapFrom)
    }
}
