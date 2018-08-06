package home.oleg.placenearme.domain.interactors;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import home.oleg.placenearme.domain.models.Venue;
import home.oleg.placenearme.domain.repositories.VenueRepository;
import io.reactivex.Observable;

/**
 * Created by Oleg on 18.04.2016.
 */
public class MapInteractor {

    private final VenueRepository placesRepository;

    public MapInteractor(VenueRepository placesRepository) {
        this.placesRepository = placesRepository;
    }

    public Observable<List<Venue>> getPlaces(Parameters params) {
        return placesRepository.getPlaces(params);
    }

    public static final class Parameters {

        private double latitude;
        private double longitude;
        private String section = "topPicks"; // by default
        private int radius;
        private int openNow = 1;
        private int venuePhotos = 1;

        public Parameters setLatitude(double latitude) {
            this.latitude = latitude;
            return this;
        }

        public Parameters setLongitude(double longitude) {
            this.longitude = longitude;
            return this;
        }

        public Parameters setRadius(int radius) {
            this.radius = radius;
            return this;
        }

        public Parameters setSection(String section) {
            if (section != null) {
                this.section = section;
            }
            return this;
        }

        public Parameters setOpenNow(int openNow) {
            this.openNow = openNow;
            return this;
        }

        public Parameters setVenuePhotos(int venuePhotos) {
            this.venuePhotos = venuePhotos;
            return this;
        }

        public double getLatitude() {
            return latitude;
        }

        public double getLongitude() {
            return longitude;
        }

        public String getSection() {
            return section;
        }

        public int getRadius() {
            return radius;
        }

        public int getOpenNow() {
            return openNow;
        }

        public int getVenuePhotos() {
            return venuePhotos;
        }

    }

}



