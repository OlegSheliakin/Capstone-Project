package home.oleg.placesnearme.presentation.feature.map.viewmodel;

import android.arch.lifecycle.ViewModel;

import java.util.List;

import javax.inject.Inject;

import home.oleg.placenearme.domain.interactors.MapInteractor;
import home.oleg.placenearme.domain.models.Venue;
import home.oleg.placenearme.domain.repositories.VenueRepository;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by Oleg on 18.04.2016.
 */
public class MapViewModel extends ViewModel {

    private VenueRepository venueRepository;

    @Inject
    public MapViewModel(VenueRepository venueRepository) {
        this.venueRepository = venueRepository;
    }

    public Observable<List<Venue>> search(MapInteractor.Parameters parameters) {
        return venueRepository.explore(parameters)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

}
