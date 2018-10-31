package home.oleg.feature_widget

import android.appwidget.AppWidgetManager
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.widget.RemoteViews
import android.widget.RemoteViewsService

import java.io.IOException
import java.util.ArrayList
import java.util.Collections

import home.oleg.placenearme.interactors.EvaluateDistance
import home.oleg.placenearme.interactors.GetRecommendedVenues
import home.oleg.placesnearme.AppApiProvider
import home.oleg.placesnearme.core_presentation.mapper.VenueMapViewMapper
import home.oleg.placesnearme.core_presentation.utils.DistanceUtil
import home.oleg.placesnearme.core_presentation.utils.ImageLoader
import home.oleg.placesnearme.core_presentation.viewdata.PreviewVenueViewData

class ListRemoteViewsFactory(private val context: Context, intent: Intent) : RemoteViewsService.RemoteViewsFactory {

    private val data = ArrayList<PreviewVenueViewData>()
    private val getRecommendedVenues: GetRecommendedVenues
    private val evaluateDistance: EvaluateDistance
    private val widgetID: Int

    init {
        this.getRecommendedVenues = (context.applicationContext as AppApiProvider).appApi.recommendedVenues
        this.evaluateDistance = (context.applicationContext as AppApiProvider).appApi.evaluateDistance
        widgetID = intent.getIntExtra(AppWidgetManager.EXTRA_APPWIDGET_ID,
                AppWidgetManager.INVALID_APPWIDGET_ID)
    }

    override fun onCreate() {

    }

    override fun onDataSetChanged() {
        data.clear()
        data.addAll(getRecommendedVenues.recommendedSection
                .flatMap { pair ->
                    evaluateDistance.evaluateDistanceVenue(pair.second)
                            .map { Pair(pair.first, pair.second) }
                }
                .map { sectionListPair -> VenueMapViewMapper.map(sectionListPair.first, sectionListPair.second) }
                .onErrorReturnItem(emptyList())
                .blockingGet())
    }

    override fun onDestroy() {

    }

    override fun getCount(): Int {
        return data.size
    }

    override fun getViewAt(position: Int): RemoteViews {
        val rView = RemoteViews(context.packageName,
                R.layout.item_widget_list)
        rView.setTextViewText(R.id.tvName, data[position].title)

        val distance = DistanceUtil.convertDistanceTOString(data[position].distance, context)
        rView.setTextViewText(R.id.tbDistance, distance)
        try {
            val b = ImageLoader.loadIcon(rView, R.id.ivIcon, data[position].categoryIconUrl, widgetID)
            rView.setImageViewBitmap(R.id.ivIcon, b)
        } catch (e: IOException) {
            e.printStackTrace()
        }

        return rView
    }

    override fun getLoadingView(): RemoteViews {
        return RemoteViews(context.packageName, R.layout.item_loading_list)
    }

    override fun getViewTypeCount(): Int {
        return 1
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun hasStableIds(): Boolean {
        return true
    }
}
