package home.oleg.placesnearme.feature_map.state;

import android.support.annotation.NonNull;

import java.util.Collections;
import java.util.List;
import java.util.Map;

import home.oleg.placesnearme.core_presentation.base.ErrorEvent;
import home.oleg.placesnearme.core_presentation.viewdata.PreviewVenueViewData;

public class MapViewState {

    private final boolean isVenuesLoading;
    private final boolean isSearchShown;
    private final ErrorEvent error;

    private MapViewState(boolean isVenuesLoading,
                         boolean isSearchShown,
                         ErrorEvent error) {
        this.isVenuesLoading = isVenuesLoading;
        this.isSearchShown = isSearchShown;
        this.error = error;
    }

    public static MapViewState initial() {
        return new MapViewState(false, false, null);
    }

    public Builder toBuilder() {
        MapViewState.Builder builder = new Builder();
        builder.error(error);
        builder.searchShown(isSearchShown);
        builder.venueLoading(isVenuesLoading);

        return builder;
    }

    public static class Builder {
        private boolean isVenuesLoading;
        private boolean isSearchShown;
        private ErrorEvent error;

        public Builder venueLoading(boolean isVenuesLoading) {
            this.isVenuesLoading = isVenuesLoading;
            return this;
        }

        public Builder searchShown(boolean isSearchShown) {
            this.isSearchShown = isSearchShown;
            return this;
        }

        public Builder error(ErrorEvent error) {
            this.error = error;
            return this;
        }

        public MapViewState build() {
            return new MapViewState(isVenuesLoading, isSearchShown, error);
        }
    }

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
