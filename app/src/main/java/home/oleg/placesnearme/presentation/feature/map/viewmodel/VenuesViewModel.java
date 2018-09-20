package home.oleg.placesnearme.presentation.feature.map.viewmodel;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import home.oleg.placenearme.interactors.GetRecomendedVenuesInteractor;
import home.oleg.placenearme.models.Section;
import home.oleg.placesnearme.presentation.base.BaseViewModel;
import home.oleg.placesnearme.presentation.feature.map.view.VenuesView;
import home.oleg.placesnearme.presentation.feature.venue.viewmodel.VenueViewModel;
import home.oleg.placesnearme.presentation.view_action.ShowVenuesAction;
import home.oleg.placesnearme.presentation.view_action.ViewActions;
import home.oleg.placesnearme.presentation.viewdata.VenueViewData;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import io.reactivex.subjects.PublishSubject;

/**
 * Created by Oleg on 10.08.2018.
 */
public final class VenuesViewModel extends BaseViewModel<VenuesView> {

    private final GetRecomendedVenuesInteractor interactor;

    private final VenuesViewModel.VenuesHolder venuesHolder = new VenuesViewModel.VenuesHolder();

    public VenuesViewModel(@NonNull GetRecomendedVenuesInteractor interactor) {
        this.interactor = interactor;
    }

    public void getRecommendedVenues() {
      /*  VenueViewData venueViewData = new VenueViewData();
        venueViewData.setDescription("asdasdasdasdasdasdadasdasdasdasd");
        venueViewData.setAddress("asdadasdasdasd");
        venueViewData.setSectionType(Section.ARTS);
        venueViewData.setTitle("Патрик и Мари");
        venueViewData.setLat(45.039279);
        venueViewData.setLng(38.997458);
        setAction(ShowVenuesAction.create(Collections.singletonList(venueViewData)));*/
        addToDisposables(interactor.getRecommendedSection()
                .map(VenueViewData::mapFrom)
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

    public void setVenues(Map<String, VenueViewData> venues) {
        venuesHolder.set(venues);
    }

    public VenueViewData getVenue(String id) {
        return venuesHolder.get(id);
    }

    private static final class VenuesHolder {

        private Map<String, VenueViewData> venues;

        private VenuesHolder() {
        }

        public void set(Map<String, VenueViewData> venues) {
            this.venues = new HashMap<>(venues);
        }

        public VenueViewData get(String id) {
            return venues.get(id);
        }
    }

}
