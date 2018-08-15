package home.oleg.placenearme.repositories;

import java.util.List;
import java.util.Objects;

import home.oleg.placenearme.models.Venue;
import io.reactivex.Single;

public interface VenueRepository {

    Single<List<Venue>> getRecommendedByCategory(Category category, Filter filter);

    Single<List<Venue>> search(String query, Filter filter);

    class Filter {
        private int radius = 1000;
        private double lat;
        private double lng;

        public int getRadius() {
            return radius;
        }

        public void setRadius(int radius) {
            this.radius = radius;
        }

        public double getLat() {
            return lat;
        }

        public void setLat(double lat) {
            this.lat = lat;
        }

        public double getLng() {
            return lng;
        }

        public void setLng(double lng) {
            this.lng = lng;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Filter filter = (Filter) o;
            return radius == filter.radius &&
                    Double.compare(filter.lat, lat) == 0 &&
                    Double.compare(filter.lng, lng) == 0;
        }

        @Override
        public int hashCode() {

            return Objects.hash(radius, lat, lng);
        }
    }

}
