package home.oleg.placesnearme.feature_map.mapper

import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import home.oleg.placesnearme.corepresentation.viewdata.PreviewVenueViewData
import javax.inject.Inject

/**
 * Created by Oleg Sheliakin on 14.08.2018.
 * Contact me by email - olegsheliakin@gmail.com
 */
class MarkerMapper @Inject constructor(private val markerIconProvider: MarkerIconProvider) {

    fun mapFrom(venueViewData: PreviewVenueViewData): Pair<MarkerOptions, PreviewVenueViewData> {
        val section = venueViewData.sectionType ?: throw IllegalStateException("must have section type")

        val latLng = LatLng(venueViewData.lat, venueViewData.lng)

        return Pair(MarkerOptions()
                .icon(markerIconProvider.getIconByCategory(section))
                .position(latLng), venueViewData)
    }

    fun mapFrom(venueViewDatas: Collection<PreviewVenueViewData>): List<Pair<MarkerOptions, PreviewVenueViewData>> {
        if (venueViewDatas.isEmpty()) {
            return emptyList()
        }

        return venueViewDatas.map(this::mapFrom)
    }
}
