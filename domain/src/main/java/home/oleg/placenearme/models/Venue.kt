package home.oleg.placenearme.models

data class Venue(
    val category: Category? = null,
    val id: String,
    val location: Location,
    val name: String,
    val distance: Double = 0.toDouble())
