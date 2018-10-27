package home.oleg.placesnearme.feature_map.state;

import android.support.annotation.NonNull;

import java.util.Collections;
import java.util.List;

import home.oleg.placesnearme.core_presentation.base.ErrorEvent;
import home.oleg.placesnearme.core_presentation.viewdata.PreviewVenueViewData;

public class MapViewState {

    @NonNull
  //  private final List<PreviewVenueViewData> venueViewDataList;
    private final boolean isVenuesLoading;
    private final boolean isSearchShown;
    private final ErrorEvent error;

    public MapViewState(/*List<PreviewVenueViewData> venueViewDataList,*/
                        boolean isVenuesLoading,
                        boolean isSearchShown,
                        ErrorEvent error) {
     //   this.venueViewDataList = venueViewDataList;
        this.isVenuesLoading = isVenuesLoading;
        this.isSearchShown = isSearchShown;
        this.error = error;
    }

    public static MapViewState initial() {
        return new MapViewState(/*Collections.emptyList(),*/ false, false, null);
    }

    public static MapViewState loading(MapViewState previous, boolean isLoading) {
        return new MapViewState(/*previous.getVenueViewDataList(),*/ isLoading, previous.isSearchShown, null);
    }

    public static MapViewState showVenues(MapViewState previous, List<PreviewVenueViewData> venueViewDataList) {
        return new MapViewState(/*venueViewDataList,*/ false, previous.isSearchShown, null);
    }

    public static MapViewState error(MapViewState previous, ErrorEvent errorEvent) {
        return new MapViewState(/*previous.getVenueViewDataList(),*/ false, previous.isSearchShown, errorEvent);
    }

   /* public List<PreviewVenueViewData> getVenueViewDataList() {
        return venueViewDataList;
    }*/

    public boolean isVenuesLoading() {
        return isVenuesLoading;
    }

    public boolean isSearchShown() {
        return isSearchShown;
    }

    public ErrorEvent getError() {
        return error;
    }
}
