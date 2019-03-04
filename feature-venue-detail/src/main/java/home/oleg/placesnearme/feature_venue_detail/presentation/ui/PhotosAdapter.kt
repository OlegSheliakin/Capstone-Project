package home.oleg.placesnearme.feature_venue_detail.presentation.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.annotation.NonNull
import androidx.recyclerview.widget.RecyclerView
import com.oleg.placesnearme.feature_venue_detail.R
import home.oleg.placesnearme.corepresentation.utils.ImageLoader
import home.oleg.placesnearme.corepresentation.viewdata.PhotoViewData
import java.util.*

internal class PhotosAdapter : RecyclerView.Adapter<PhotosAdapter.ViewHolder>() {

    private val photoViewDatas = ArrayList<PhotoViewData>()

    @NonNull
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_photo, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(@NonNull holder: ViewHolder, position: Int) {
        holder.bind(photoViewDatas[position])
    }

    override fun getItemCount(): Int {
        return photoViewDatas.size
    }

    override fun onViewRecycled(holder: ViewHolder) {
        super.onViewRecycled(holder)
        ImageLoader.clear(holder.imageView)
    }

    fun setItems(items: List<PhotoViewData>) {
        photoViewDatas.clear()
        photoViewDatas.addAll(items)
        notifyDataSetChanged()
    }

    internal class ViewHolder internal constructor(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val imageView: ImageView

        init {
            imageView = itemView.findViewById(R.id.ivPhoto)
        }

        fun bind(photoViewData: PhotoViewData) {
            ImageLoader.loadImage(imageView, photoViewData.fullSizeUrl)
        }
    }
}
