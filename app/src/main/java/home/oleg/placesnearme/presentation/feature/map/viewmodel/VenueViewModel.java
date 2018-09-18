package home.oleg.placesnearme.presentation.feature.map.viewmodel;

import home.oleg.placenearme.interactors.GetRecomendedVenuesInteractor;
import home.oleg.placesnearme.presentation.feature.map.view.VenueView;
import home.oleg.placesnearme.presentation.view_action.ShowVenueDataAction;
import home.oleg.placesnearme.presentation.view_action.ViewActions;
import home.oleg.placesnearme.presentation.viewdata.VenueViewData;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by Oleg on 10.08.2018.
 */
public final class VenueViewModel extends BaseViewModel<VenueView> {

    private final GetRecomendedVenuesInteractor interactor;

    public VenueViewModel(@NonNull GetRecomendedVenuesInteractor interactor) {
        this.interactor = interactor;
    }

    public void getRecommendedVenues() {
        addToDisposables(interactor.getRecommendedSection()
                .map(VenueViewData::mapFrom)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe(d -> setAction(ViewActions.showLoading()))
                .subscribe(venues -> {
                            setAction(ViewActions.hideLoading());
                            setAction(ShowVenueDataAction.create(venues));
                        },
                        throwable -> {
                            setAction(ViewActions.hideLoading());
                            setAction(ViewActions.error(throwable));
                        })
        );
    }

}
