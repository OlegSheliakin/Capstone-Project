package home.oleg.placesnearme.feature_map.state;

import android.support.annotation.NonNull;

import java.util.Collections;
import java.util.List;
import java.util.Map;

import home.oleg.placesnearme.core_presentation.viewdata.PreviewVenueViewData;

public class MapViewState {

    @NonNull
    private final List<PreviewVenueViewData> venueViewDataList;
    private final boolean isVenuesLoading;
    private final Throwable error;

    public MapViewState(List<PreviewVenueViewData> venueViewDataList,
                        boolean isVenuesLoading,
                        Throwable throwable) {
        this.venueViewDataList = venueViewDataList;
        this.isVenuesLoading = isVenuesLoading;
        this.error = throwable;
    }

    public static MapViewState initial() {
        return new MapViewState(Collections.emptyList(), false, null);
    }

    public static MapViewState loading(MapViewState previous) {
        return new MapViewState(previous.getVenueViewDataList(), true, null);
    }

    public static MapViewState showVenues(MapViewState previous, List<PreviewVenueViewData> venueViewDataList) {
        return new MapViewState(venueViewDataList, false, null);
    }

    public static MapViewState error(MapViewState previous, Throwable throwable) {
        return new MapViewState(previous.getVenueViewDataList(), false, throwable);
    }

    public static MapViewState errorShown(MapViewState previous) {
        return new MapViewState(previous.getVenueViewDataList(), previous.isVenuesLoading, null);
    }

    public List<PreviewVenueViewData> getVenueViewDataList() {
        return venueViewDataList;
    }

    public boolean isVenuesLoading() {
        return isVenuesLoading;
    }

    public Throwable getError() {
        return error;
    }

}
