package home.oleg.placesnearme.core_presentation.recyclerview

import androidx.recyclerview.widget.DiffUtil
import io.reactivex.annotations.NonNull

class VenuesDiffCallback private constructor() : DiffUtil.ItemCallback<ItemViewType>() {

    override fun areItemsTheSame(itemViewType: ItemViewType, t1: ItemViewType): Boolean {
        return itemViewType == t1
    }

    override fun areContentsTheSame(itemViewType: ItemViewType, t1: ItemViewType): Boolean {
        return itemViewType == t1
    }

    companion object {

        var VENUES_DIFF_CALLBACK = VenuesDiffCallback()
    }

}
