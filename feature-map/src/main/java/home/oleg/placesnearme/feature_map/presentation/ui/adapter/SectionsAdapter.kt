package home.oleg.placesnearme.feature_map.presentation.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView

import androidx.recyclerview.widget.RecyclerView
import home.oleg.placenearme.models.Section
import home.oleg.placesnearme.feature_map.R
import home.oleg.placesnearme.feature_map.presentation.sections.SectionViewData

/**
 * Created by Oleg Sheliakin on 25.10.2018.
 * Contact me by email - olegsheliakin@gmail.com
 */
class SectionsAdapter(
        private val checkedItems: List<CheckedItem<SectionViewData>>,
        private val sectionSelectListener: SectionSelectListener)
    : RecyclerView.Adapter<SectionsAdapter.ViewHolder>(), ItemSelectListener {

    val selectedItemPosition: Int
        get() {
            var selectedItemPosition = -1
            for (i in checkedItems.indices) {
                if (checkedItems[i].isChecked) {
                    selectedItemPosition = i
                }
            }
            return selectedItemPosition
        }

    val selectedItem: Section?
        get() {
            val selectedItemPosition = selectedItemPosition
            return if (selectedItemPosition != -1) {
                checkedItems[selectedItemPosition].data.section
            } else null
        }

    interface SectionSelectListener {
        fun sectionSelected(section: Section)
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, i: Int): ViewHolder {
        val view = LayoutInflater.from(viewGroup.context).inflate(R.layout.item_section, viewGroup, false)
        return SectionsAdapter.ViewHolder(view, this)
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, i: Int) {
        viewHolder.bind(checkedItems[i])
    }

    override fun getItemCount(): Int {
        return checkedItems.size
    }

    override fun getItemId(position: Int): Long = position.toLong()

    override fun itemSelected(position: Int) {
        setSelected(position)
        sectionSelectListener.sectionSelected(checkedItems[position].data.section)
    }

    fun setSelected(position: Int) {
        if (position == -1) {
            return
        }

        for (checkedItem in checkedItems) {
            checkedItem.isChecked = false
        }
        checkedItems[position].isChecked = true
        notifyDataSetChanged()
    }

    class ViewHolder constructor(itemView: View, itemSelectListener: ItemSelectListener) : RecyclerView.ViewHolder(itemView) {

        private val textView: TextView = itemView.findViewById(R.id.tvSection)

        init {
            textView.setOnClickListener { itemSelectListener.itemSelected(adapterPosition) }
        }

        fun bind(checkedItem: CheckedItem<SectionViewData>) {
            val (_, sectionNameRes, sectionDrawableRes) = checkedItem.data
            textView.isSelected = checkedItem.isChecked
            textView.setText(sectionNameRes)
            textView.setCompoundDrawablesWithIntrinsicBounds(0, sectionDrawableRes, 0, 0)
        }
    }
}
