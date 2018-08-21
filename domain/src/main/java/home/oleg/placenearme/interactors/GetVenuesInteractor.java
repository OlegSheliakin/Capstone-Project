package home.oleg.placenearme.interactors;

import java.util.List;

import home.oleg.placenearme.models.DetailedVenue;
import home.oleg.placenearme.models.UserLocation;
import home.oleg.placenearme.models.Venue;
import home.oleg.placenearme.repositories.Category;
import home.oleg.placenearme.repositories.CategoryRepository;
import home.oleg.placenearme.repositories.DetailedVenueRepository;
import home.oleg.placenearme.repositories.UserLocationRepository;
import home.oleg.placenearme.repositories.VenueRepository;
import home.oleg.placenearme.repositories.VenueRequestParams;
import io.reactivex.Observable;
import io.reactivex.Single;
import io.reactivex.SingleSource;
import io.reactivex.functions.Function;

public class GetVenuesInteractor {

    private final VenueRepository venueRepository;
    private final DetailedVenueRepository detailedVenueRepository;
    private final UserLocationRepository locationRepository;
    private final CategoryRepository categoryRepository;

    public GetVenuesInteractor(VenueRepository venueRepository,
                               DetailedVenueRepository detailedVenueRepository,
                               UserLocationRepository locationRepository,
                               CategoryRepository categoryRepository) {
        this.venueRepository = venueRepository;
        this.detailedVenueRepository = detailedVenueRepository;
        this.locationRepository = locationRepository;
        this.categoryRepository = categoryRepository;
    }

    public Single<List<DetailedVenue>> getRecommendedVenues() {
        return getVenues(userLocation ->
                venueRepository.getRecommendedByCategory(
                        categoryRepository.getMostFrequent(), createFilter(userLocation)));
    }

    public Single<List<DetailedVenue>> getRecommendedVenue(Category category) {
        return getVenues(userLocation ->
                venueRepository.getRecommendedByCategory(category, createFilter(userLocation)));
    }

    public Single<List<DetailedVenue>> searchVenue(String query) {
        return getVenues(userLocation ->
                venueRepository.search(query, createFilter(userLocation)));
    }

    private Single<List<DetailedVenue>> getVenues(
            Function<UserLocation, SingleSource<List<Venue>>> mapper) {
        return locationRepository.getLocation()
                .flatMap(mapper)
                .flatMapObservable(Observable::fromIterable)
                .map(Venue::getId)
                .flatMapSingle(detailedVenueRepository::getDetailedVenueById)
                .toList();
    }

    private VenueRequestParams createFilter(UserLocation userLocation) {
        return VenueRequestParams.withLocation(
                userLocation.getLat(),
                userLocation.getLng());
    }

}
