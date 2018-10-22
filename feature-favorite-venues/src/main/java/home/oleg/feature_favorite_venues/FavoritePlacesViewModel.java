package home.oleg.feature_favorite_venues;

import home.oleg.placenearme.interactors.GetFavoriteVenues;
import home.oleg.placesnearme.core_presentation.base.BaseViewModel;
import home.oleg.placesnearme.core_presentation.viewdata.VenueViewData;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by Oleg Sheliakin on 04.09.2018.
 * Contact me by email - olegsheliakin@gmail.com
 */
public class FavoritePlacesViewModel extends BaseViewModel<FavoriteView> {

    private final GetFavoriteVenues getFavoriteVenues;

    public FavoritePlacesViewModel(GetFavoriteVenues getFavoriteVenues) {
        this.getFavoriteVenues = getFavoriteVenues;
    }

    @Override
    protected void onObserverCreated() {
        super.onObserverCreated();
        addToDisposables(getFavoriteVenues.execute()
                .map(VenueViewData::mapFrom)
                .map(VenueViewItem::map)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(detailedVenues -> {
                    if (!detailedVenues.isEmpty()) {
                        setAction(favoriteView -> favoriteView.showData(detailedVenues));
                    } else {
                        setAction(FavoriteView::showEmpty);
                    }

                }, Throwable::printStackTrace));
    }

}