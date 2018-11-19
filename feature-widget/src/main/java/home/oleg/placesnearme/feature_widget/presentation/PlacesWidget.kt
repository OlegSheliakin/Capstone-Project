package home.oleg.placesnearme.feature_widget.presentation

import android.app.PendingIntent
import android.appwidget.AppWidgetManager
import android.appwidget.AppWidgetProvider
import android.content.Context
import android.content.Intent
import android.widget.RemoteViews
import home.oleg.placesnearme.feature_widget.R

/**
 * Implementation of App Widget functionality.
 */
class PlacesWidget : AppWidgetProvider() {

    override fun onUpdate(context: Context, appWidgetManager: AppWidgetManager, appWidgetIds: IntArray) {
        // There may be multiple widgets active, so update all of them
        for (appWidgetId in appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId)
        }
    }

    override fun onEnabled(context: Context) {
        // Enter relevant functionality for when the first widget is created
    }

    override fun onDisabled(context: Context) {
        // Enter relevant functionality for when the last widget is disabled
    }

    companion object {

        internal fun updateAppWidget(context: Context, appWidgetManager: AppWidgetManager,
                                     appWidgetId: Int) {
            val rv = RemoteViews(context.packageName,
                    R.layout.places_widget)
            val adapter = Intent(context, PlaceRemoteService::class.java)
            adapter.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetId)
            rv.setRemoteAdapter(R.id.list, adapter)

            val updateIntent = Intent(context, PlacesWidget::class.java)
            updateIntent.action = AppWidgetManager.ACTION_APPWIDGET_UPDATE
            updateIntent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_IDS,
                    intArrayOf(appWidgetId))
            val pIntent = PendingIntent.getBroadcast(context, appWidgetId, updateIntent, 0)
            rv.setOnClickPendingIntent(R.id.refresh, pIntent)
            appWidgetManager.updateAppWidget(appWidgetId, rv)
            appWidgetManager.notifyAppWidgetViewDataChanged(appWidgetId, R.id.list)
        }
    }
}

