package home.oleg.placenearme.models

data class DetailedVenue(val category: Category? = null,
                         val contact: Contact? = null,
                         val description: String? = null,
                         val hours: Hours? = null,
                         val id: String,
                         val location: Location,
                         val name: String,
                         val photos: List<Photo> = emptyList(),
                         val isHereNow: Boolean = false,
                         val isFavorite: Boolean = false,
                         val rating: Float = 0.toFloat(),
                         val distance: Double = 0.toDouble())
