package home.oleg.placesnearme.feature_map.viewmodel;

import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import home.oleg.placenearme.interactors.EvaluateDistance;
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
                .doOnSubscribe(d -> state.setValue(MapViewState.loading(state.getValue(), true)))
                .subscribe(venues -> {
                            state.setValue(MapViewState.loading(state.getValue(), false));
                            data.setValue(venues);
                        },
                        throwable -> {
                            ErrorEvent errorEvent = errorHanlder.handle(throwable);
                            state.setValue(MapViewState.error(state.getValue(), errorEvent));
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
