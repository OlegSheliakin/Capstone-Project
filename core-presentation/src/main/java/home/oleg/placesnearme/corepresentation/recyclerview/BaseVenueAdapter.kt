package home.oleg.placesnearme.corepresentation.recyclerview

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import androidx.annotation.LayoutRes
import androidx.annotation.NonNull
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.smedialink.common.recyclerview.ItemViewType

/**
 * Created by Oleg Sheliakin on 23.10.2018.
 * Contact me by email - olegsheliakin@gmail.com
 */
abstract class BaseVenueAdapter
protected constructor(diffCallback: DiffUtil.ItemCallback<ItemViewType>)
    : ListAdapter<ItemViewType, RecyclerView.ViewHolder>(diffCallback) {

    @get:LayoutRes
    protected abstract val venueItemLayoutRes: Int

    @get:LayoutRes
    protected abstract val emptyItemLayoutRes: Int

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            1 -> {
                val venueView = LayoutInflater.from(viewGroup.context)
                        .inflate(venueItemLayoutRes, viewGroup, false)
                getVenueItemHolder(venueView)
            }
            else -> {
                val emptyView = LayoutInflater.from(viewGroup.context)
                        .inflate(emptyItemLayoutRes, viewGroup, false)
                EmptyViewHolder(emptyView)
            }
        }
    }

    override fun onBindViewHolder(viewHolder: RecyclerView.ViewHolder, i: Int) {
        val itemViewType = getItem(i)

        if (itemViewType is VenueViewItem && viewHolder is BaseVenueAdapter.VenueItemHolder) {
            viewHolder.bind(itemViewType)
        }
    }

    override fun getItemViewType(position: Int): Int {
        return getItem(position).viewType
    }

    fun showEmpty() {

    }

    abstract class VenueItemHolder(@NonNull itemView: View) : RecyclerView.ViewHolder(itemView) {

        abstract fun bind(venueViewItem: VenueViewItem)
    }

    private class EmptyViewHolder constructor(itemView: View) : RecyclerView.ViewHolder(itemView)

    protected abstract fun getVenueItemHolder(view: View): BaseVenueAdapter.VenueItemHolder

}
