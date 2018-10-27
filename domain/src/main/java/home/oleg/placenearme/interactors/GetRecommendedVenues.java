package home.oleg.placenearme.interactors;

import com.smedialink.common.Pair;

import java.util.List;

import home.oleg.placenearme.models.LatLng;
import home.oleg.placenearme.models.Section;
import home.oleg.placenearme.models.UserLocation;
import home.oleg.placenearme.models.Venue;
import home.oleg.placenearme.repositories.DistanceRepository;
import home.oleg.placenearme.repositories.SectionRepository;
import home.oleg.placenearme.repositories.UserLocationRepository;
import home.oleg.placenearme.repositories.VenueRepository;
import home.oleg.placenearme.repositories.VenueRequestParams;
import io.reactivex.Single;
import io.reactivex.annotations.NonNull;

public class GetRecommendedVenues {
    private final VenueRepository venueRepository;
    private final UserLocationRepository locationRepository;
    private final SectionRepository categoryRepository;

    public GetRecommendedVenues(VenueRepository venueRepository,
                                UserLocationRepository locationRepository,
                                SectionRepository categoryRepository) {
        this.venueRepository = venueRepository;
        this.locationRepository = locationRepository;
        this.categoryRepository = categoryRepository;
    }

    public Single<Pair<Section, List<Venue>>> getRecommendedSection() {
        Section section = categoryRepository.getMostFrequent();
        return getVenues(section);
    }

    public Single<Pair<Section, List<Venue>>> getRecommendedSection(@NonNull Section section) {
        return getVenues(section);
    }

    private Single<Pair<Section, List<Venue>>> getVenues(Section section) {
        return locationRepository.getLocation()
                .flatMap(userLocation ->
                        venueRepository.getRecommendedBySection(section, createFilter(userLocation))
                )
                .map(venues -> new Pair<>(section, venues));
    }


    private VenueRequestParams createFilter(UserLocation userLocation) {
        return VenueRequestParams.withLocation(
                userLocation.getLat(),
                userLocation.getLng());
    }

}
