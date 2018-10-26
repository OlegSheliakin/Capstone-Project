package home.oleg.placesnearme.feature_map.viewmodel;

import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

import java.util.HashMap;
import java.util.Map;

import home.oleg.placenearme.interactors.GetRecommendedVenues;
import home.oleg.placenearme.models.Section;
import home.oleg.placesnearme.core_presentation.mapper.VenueMapViewMapper;
import home.oleg.placesnearme.core_presentation.viewdata.PreviewVenueViewData;
import home.oleg.placesnearme.feature_map.state.LocationViewState;
import home.oleg.placesnearme.feature_map.state.MapViewState;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by Oleg on 10.08.2018.
 */
public final class VenuesViewModel extends ViewModel {

    private Disposable searchDisposable;
    private final GetRecommendedVenues interactor;
    private final VenuesViewModel.VenuesHolder venuesHolder = new VenuesViewModel.VenuesHolder();
    private final MutableLiveData<MapViewState> state = new MutableLiveData<>();

    public VenuesViewModel(@NonNull GetRecommendedVenues interactor) {
        this.interactor = interactor;
        state.setValue(MapViewState.initial());
    }

    public MutableLiveData<MapViewState> getState() {
        return state;
    }

    public void getRecommendedVenues(Section section) {
        if (searchDisposable != null && !searchDisposable.isDisposed()) {
            searchDisposable.dispose();
        }

        searchDisposable = interactor.getRecommendedSection(section)
                .map(sectionListPair -> VenueMapViewMapper.map(sectionListPair.getFirst(), sectionListPair.getSecond()))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe(d -> state.setValue(MapViewState.loading(state.getValue())))
                .subscribe(venues -> {
                            state.setValue(MapViewState.showVenues(state.getValue(), venues));
                        },
                        throwable -> {
                            state.setValue(MapViewState.error(state.getValue(), throwable));
                        });
    }

    public void setVenues(Map<String, PreviewVenueViewData> venues) {
        venuesHolder.set(venues);
    }

    public void errorShown() {
        state.setValue(MapViewState.errorShown(state.getValue()));
    }

    public PreviewVenueViewData getVenue(String id) {
        return venuesHolder.get(id);
    }

    private static final class VenuesHolder {

        private Map<String, PreviewVenueViewData> venues;

        private VenuesHolder() {
        }

        private void set(Map<String, PreviewVenueViewData> venues) {
            this.venues = new HashMap<>(venues);
        }

        private PreviewVenueViewData get(String id) {
            return venues.get(id);
        }
    }

}
