package home.oleg.placesnearme.feature_widget.presentation.ui

import android.appwidget.AppWidgetManager
import android.content.Context
import android.content.Intent
import android.widget.RemoteViews
import android.widget.RemoteViewsService
import home.oleg.placesnearme.corepresentation.mapper.VenueMapViewMapper
import home.oleg.placesnearme.corepresentation.utils.DistanceUtil
import home.oleg.placesnearme.corepresentation.utils.ImageLoader
import home.oleg.placesnearme.corepresentation.viewdata.PreviewPlace
import home.oleg.placesnearme.coredomain.interactors.EvaluateDistance
import home.oleg.placesnearme.coredomain.interactors.GetRecommendedVenues
import home.oleg.placesnearme.feature_widget.R
import home.oleg.placesnearme.feature_widget.di.PlacesWidgetComponent
import java.io.IOException
import java.util.*
import javax.inject.Inject

class ListRemoteViewsFactory(private val context: Context, intent: Intent) : RemoteViewsService.RemoteViewsFactory {

    @Inject
    lateinit var getRecommendedVenues: GetRecommendedVenues

    @Inject
    lateinit var evaluateDistance: EvaluateDistance

    private val data = ArrayList<PreviewPlace>()
    private val widgetID: Int = intent.getIntExtra(AppWidgetManager.EXTRA_APPWIDGET_ID,
            AppWidgetManager.INVALID_APPWIDGET_ID)

    override fun onCreate() {
        PlacesWidgetComponent.Injector.inject(context, this)
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

        val distance = DistanceUtil.convertDistanceToString(data[position].distance, context)
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
