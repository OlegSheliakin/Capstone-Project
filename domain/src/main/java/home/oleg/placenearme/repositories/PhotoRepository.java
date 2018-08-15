package home.oleg.placenearme.repositories;

import java.util.List;

import home.oleg.placenearme.models.Photo;
import io.reactivex.Observable;

public interface PhotoRepository {

    Observable<List<Photo>> getPhotosById(String venueId);

}
