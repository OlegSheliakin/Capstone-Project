package home.oleg.placesnearme.feature_map.presentation.ui.adapter

import java.util.ArrayList

/**
 * Created by Oleg Sheliakin on 25.10.2018.
 * Contact me by email - olegsheliakin@gmail.com
 */
class CheckedItem<out T>(var isChecked: Boolean, val data: T) {
    companion object {

        fun <T> wrap(dataList: List<T>): List<CheckedItem<T>> {
            val result = ArrayList<CheckedItem<T>>()

            for (data in dataList) {
                result.add(CheckedItem(false, data))
            }

            return result
        }
    }
}
