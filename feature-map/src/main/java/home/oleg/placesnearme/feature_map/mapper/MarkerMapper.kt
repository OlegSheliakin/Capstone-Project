package home.oleg.placesnearme.feature_map.mapper

import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import home.oleg.placesnearme.corepresentation.viewdata.PreviewPlace
import javax.inject.Inject

/**
 * Created by Oleg Sheliakin on 14.08.2018.
 * Contact me by email - olegsheliakin@gmail.com
 */
class MarkerMapper @Inject constructor(private val markerIconProvider: MarkerIconProvider) {

    fun mapFrom(places: PreviewPlace): Pair<MarkerOptions, PreviewPlace> {
        val section = places.sectionType ?: throw IllegalStateException("must have section type")

        val latLng = LatLng(places.lat, places.lng)

        return Pair(MarkerOptions()
                .icon(markerIconProvider.getIconByCategory(section))
                .position(latLng), places)
    }

    fun mapFrom(places: Collection<PreviewPlace>): List<Pair<MarkerOptions, PreviewPlace>> {
        if (places.isEmpty()) {
            return emptyList()
        }

        return places.map(this::mapFrom)
    }
}
