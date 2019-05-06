package home.oleg.placesnearme.coredomain.models

data class Venue(
        val category: Category,
        val id: String,
        val location: UserLocation,
        val name: String,
        val distance: Double = 0.toDouble())
