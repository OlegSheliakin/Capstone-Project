package home.oleg.placesnearme.network.models

data class Category(val id: String? = null,
                    val name: String? = null,
                    val pluralName: String? = null,
                    val primary: Boolean? = null,
                    val shortName: String? = null,
                    val icon: CategoryIcon? = null)
