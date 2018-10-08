package home.oleg.placesnearme.feature_map.viewmodel;

import java.util.HashMap;
import java.util.Map;

import home.oleg.placenearme.interactors.GetRecommendedVenues;
import home.oleg.placesnearme.core_presentation.base.BaseViewModel;
import home.oleg.placesnearme.core_presentation.mapper.VenueMapViewMapper;
import home.oleg.placesnearme.core_presentation.view_actions.ViewActions;
import home.oleg.placesnearme.core_presentation.viewdata.VenueMapViewData;
import home.oleg.placesnearme.feature_map.view.VenuesView;
import home.oleg.placesnearme.feature_map.view_action.ShowVenuesAction;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by Oleg on 10.08.2018.
 */
public final class VenuesViewModel extends BaseViewModel<VenuesView> {

    private final GetRecommendedVenues interactor;

    private final VenuesViewModel.VenuesHolder venuesHolder = new VenuesViewModel.VenuesHolder();

    public VenuesViewModel(@NonNull GetRecommendedVenues interactor) {
        this.interactor = interactor;
    }

    public void getRecommendedVenues() {
        addToDisposables(interactor.getRecommendedSection()
                .map(sectionListPair -> VenueMapViewMapper.map(sectionListPair.getFirst(), sectionListPair.getSecond()))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe(d -> setAction(ViewActions.showLoading()))
                .subscribe(venues -> {
                            setAction(ViewActions.hideLoading());
                            setAction(ShowVenuesAction.create(venues));
                        },
                        throwable -> {
                            setAction(ViewActions.hideLoading());
                            setAction(ViewActions.error(throwable));
                        })
        );
    }

    public void setVenues(Map<String, VenueMapViewData> venues) {
        venuesHolder.set(venues);
    }

    public VenueMapViewData getVenue(String id) {
        return venuesHolder.get(id);
    }

    private static final class VenuesHolder {

        private Map<String, VenueMapViewData> venues;

        private VenuesHolder() {
        }

        private void set(Map<String, VenueMapViewData> venues) {
            this.venues = new HashMap<>(venues);
        }

        private VenueMapViewData get(String id) {
            return venues.get(id);
        }
    }

}
