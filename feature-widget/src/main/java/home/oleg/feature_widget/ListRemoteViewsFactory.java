package home.oleg.feature_widget;

import android.appwidget.AppWidgetManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import com.smedialink.common.Pair;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import home.oleg.placenearme.interactors.EvaluateDistance;
import home.oleg.placenearme.interactors.GetRecommendedVenues;
import home.oleg.placesnearme.AppApiProvider;
import home.oleg.placesnearme.core_presentation.mapper.VenueMapViewMapper;
import home.oleg.placesnearme.core_presentation.utils.DistanceUtil;
import home.oleg.placesnearme.core_presentation.utils.ImageLoader;
import home.oleg.placesnearme.core_presentation.viewdata.PreviewVenueViewData;

public class ListRemoteViewsFactory implements RemoteViewsService.RemoteViewsFactory {

    private final List<PreviewVenueViewData> data = new ArrayList<>();
    private final GetRecommendedVenues getRecommendedVenues;
    private final EvaluateDistance evaluateDistance;
    private final Context context;
    private final int widgetID;

    public ListRemoteViewsFactory(Context context, Intent intent) {
        this.getRecommendedVenues = ((AppApiProvider) context.getApplicationContext()).getAppApi().getRecommendedVenues();
        this.evaluateDistance = ((AppApiProvider) context.getApplicationContext()).getAppApi().getEvaluateDistance();
        this.context = context;
        widgetID = intent.getIntExtra(AppWidgetManager.EXTRA_APPWIDGET_ID,
                AppWidgetManager.INVALID_APPWIDGET_ID);
    }

    @Override
    public void onCreate() {

    }

    @Override
    public void onDataSetChanged() {
        data.clear();
        data.addAll(getRecommendedVenues.getRecommendedSection()
                .flatMap(sectionListPair -> evaluateDistance.evaluateVenueDistance(sectionListPair.getSecond())
                        .map(venues -> new Pair<>(sectionListPair.getFirst(), venues))
                )
                .map(sectionListPair -> VenueMapViewMapper.map(sectionListPair.getFirst(), sectionListPair.getSecond()))
                .onErrorReturnItem(Collections.emptyList())
                .blockingGet());
    }

    @Override
    public void onDestroy() {

    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public RemoteViews getViewAt(int position) {
        RemoteViews rView = new RemoteViews(context.getPackageName(),
                R.layout.item_widget_list);
        rView.setTextViewText(R.id.tvName, data.get(position).getTitle());

        String distance = DistanceUtil.convertDistanceTOString(data.get(position).getDistance(), context);
        rView.setTextViewText(R.id.tbDistance, distance);
        try {
            Bitmap b = ImageLoader.loadIcon(rView, R.id.ivIcon, data.get(position).getCategoryIconUrl(), widgetID);
            rView.setImageViewBitmap(R.id.ivIcon, b);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return rView;
    }

    @Override
    public RemoteViews getLoadingView() {
        return new RemoteViews(context.getPackageName(), R.layout.item_loading_list);
    }

    @Override
    public int getViewTypeCount() {
        return 1;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }
}
