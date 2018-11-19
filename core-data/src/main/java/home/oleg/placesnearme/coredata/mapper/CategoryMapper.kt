package home.oleg.placesnearme.coredata.mapper

import home.oleg.placesnearme.coredomain.models.Category
import java.util.*

/**
 * Created by Oleg Sheliakin on 25.09.2018.
 * Contact me by email - olegsheliakin@gmail.com
 */
object CategoryMapper {

    fun map(category: home.oleg.placesnearme.corenetwork.models.Category) = Category(
            name = category.name,
            iconPrefix = category.icon?.prefix,
            iconSuffix = category.icon?.suffix
    )

    fun map(categories: List<home.oleg.placesnearme.corenetwork.models.Category>): List<Category> {
        val result = ArrayList<Category>()

        for (category in categories) {
            result.add(map(category))
        }

        return result
    }

}
