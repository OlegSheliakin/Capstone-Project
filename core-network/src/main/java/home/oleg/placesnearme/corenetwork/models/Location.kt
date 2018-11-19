package home.oleg.placesnearme.corenetwork.models

data class Location(
        var address: String? = null,
        var formattedAddress: List<String> = emptyList(),
        var lat: Double,
        var lng: Double)
