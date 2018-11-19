package home.oleg.placesnearme.corenetwork.models

data class Category(val id: String? = null,
                    val name: String? = null,
                    val pluralName: String? = null,
                    val primary: Boolean,
                    val shortName: String? = null,
                    val icon: CategoryIcon? = null)
