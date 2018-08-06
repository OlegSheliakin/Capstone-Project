package home.oleg.placenearme.domain.repositories;

import java.util.List;

import home.oleg.placenearme.domain.interactors.MapInteractor;
import home.oleg.placenearme.domain.models.Venue;
import io.reactivex.Observable;

public interface VenueRepository {
    Observable<List<Venue>> getPlaces(MapInteractor.Parameters parameters);
}
