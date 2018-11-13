package home.oleg.placesnearme.data.mapper

import java.util.ArrayList

import home.oleg.placenearme.models.Category

/**
 * Created by Oleg Sheliakin on 25.09.2018.
 * Contact me by email - olegsheliakin@gmail.com
 */
object CategoryMapper {

    fun map(category: home.oleg.placesnearme.network.models.Category) = Category(
            name = category.name,
            iconPrefix = category.icon?.prefix,
            iconSuffix = category.icon?.suffix
    )

    fun map(categories: List<home.oleg.placesnearme.network.models.Category>): List<Category> {
        val result = ArrayList<Category>()

        for (category in categories) {
            result.add(map(category))
        }

        return result
    }

}
