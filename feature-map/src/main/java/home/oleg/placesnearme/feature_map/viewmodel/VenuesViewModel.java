package home.oleg.placesnearme.feature_map.viewmodel;

import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

import com.smedialink.common.Optional;
import com.smedialink.common.function.Function;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import home.oleg.placenearme.interactors.GetRecommendedVenues;
import home.oleg.placenearme.models.Section;
import home.oleg.placesnearme.core_presentation.base.ErrorEvent;
import home.oleg.placesnearme.core_presentation.error_handler.ErrorHanlder;
import home.oleg.placesnearme.core_presentation.mapper.VenueMapViewMapper;
import home.oleg.placesnearme.core_presentation.viewdata.PreviewVenueViewData;
import home.oleg.placesnearme.feature_map.state.MapViewState;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by Oleg on 10.08.2018.
 */
public final class VenuesViewModel extends ViewModel {

    private Disposable searchDisposable;
    private final ErrorHanlder errorHanlder;
    private final GetRecommendedVenues interactor;
    private final VenuesViewModel.VenuesHolder venuesHolder = new VenuesViewModel.VenuesHolder();
    private final MutableLiveData<MapViewState> state = new MutableLiveData<>();
    private final MutableLiveData<List<PreviewVenueViewData>> data = new MutableLiveData<>();

    public VenuesViewModel(ErrorHanlder errorHanlder,
                           @NonNull GetRecommendedVenues interactor) {
        this.errorHanlder = errorHanlder;
        this.interactor = interactor;
        state.setValue(MapViewState.initial());
    }

    public MutableLiveData<MapViewState> getState() {
        return state;
    }

    public MutableLiveData<List<PreviewVenueViewData>> getData() {
        return data;
    }

    public void getRecommendedVenues(Section section) {
        if (searchDisposable != null && !searchDisposable.isDisposed()) {
            searchDisposable.dispose();
        }

        searchDisposable = interactor.getRecommendedSection(section)
                .map(sectionListPair -> VenueMapViewMapper.map(sectionListPair.getFirst(), sectionListPair.getSecond()))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe(d -> changeState(mapViewState -> mapViewState.toBuilder()
                        .error(null)
                        .venueLoading(true).build()))
                .subscribe(venues -> {
                            changeState(mapViewState -> mapViewState.toBuilder().venueLoading(false).build());
                            data.setValue(venues);
                        },
                        throwable -> {
                            ErrorEvent errorEvent = errorHanlder.handle(throwable);
                            changeState(mapViewState -> mapViewState.toBuilder()
                                    .venueLoading(false)
                                    .error(errorEvent).build());
                        });
    }

    public void openSearch() {
        changeState(mapViewState -> mapViewState.toBuilder().searchShown(true).build());
    }

    public void closeSearch() {
        changeState(mapViewState -> mapViewState.toBuilder().searchShown(false).build());
    }

    private void changeState(Function<MapViewState, MapViewState> function) {
        Optional.of(state.getValue()).ifPresent(mapViewState -> {
            MapViewState newState = function.apply(mapViewState);
            state.setValue(newState);
        });
    }

    public void setVenues(Map<String, PreviewVenueViewData> venues) {
        venuesHolder.set(venues);
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
