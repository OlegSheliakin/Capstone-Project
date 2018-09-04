package home.oleg.placenearme.interactors;

import com.smedialink.common.Pair;

import java.util.List;

import home.oleg.placenearme.models.DetailedVenue;
import home.oleg.placenearme.models.Section;
import home.oleg.placenearme.models.UserLocation;
import home.oleg.placenearme.models.Venue;
import home.oleg.placenearme.repositories.DetailedVenueRepository;
import home.oleg.placenearme.repositories.SectionRepository;
import home.oleg.placenearme.repositories.UserLocationRepository;
import home.oleg.placenearme.repositories.VenueRepository;
import home.oleg.placenearme.repositories.VenueRequestParams;
import io.reactivex.Observable;
import io.reactivex.Single;
import io.reactivex.annotations.NonNull;

public class GetRecomendedVenuesInteractor {
    private final VenueRepository venueRepository;
    private final DetailedVenueRepository detailedVenueRepository;
    private final UserLocationRepository locationRepository;
    private final SectionRepository categoryRepository;

    public GetRecomendedVenuesInteractor(VenueRepository venueRepository,
                                         DetailedVenueRepository detailedVenueRepository,
                                         UserLocationRepository locationRepository,
                                         SectionRepository categoryRepository) {
        this.venueRepository = venueRepository;
        this.detailedVenueRepository = detailedVenueRepository;
        this.locationRepository = locationRepository;
        this.categoryRepository = categoryRepository;
    }

    public Single<Pair<Section.Type, List<DetailedVenue>>> getRecommendedSection() {
        Section.Type type = categoryRepository.getMostFrequent();
        return getVenues(type);
    }

    public Single<Pair<Section.Type, List<DetailedVenue>>> getRecommendedSection(@NonNull Section.Type type) {
        return getVenues(type);
    }

    private Single<Pair<Section.Type, List<DetailedVenue>>> getVenues(Section.Type type) {
        return locationRepository.getLocation()
                .flatMap(userLocation -> venueRepository.getRecommendedBySection(type, createFilter(userLocation)))
                .flatMapObservable(Observable::fromIterable)
                .map(Venue::getId)
                .flatMapSingle(detailedVenueRepository::getDetailedVenueById)
                .toList()
                .map(venues -> new Pair<>(type, venues));
    }

    private VenueRequestParams createFilter(UserLocation userLocation) {
        return VenueRequestParams.withLocation(
                userLocation.getLat(),
                userLocation.getLng());
    }

}
