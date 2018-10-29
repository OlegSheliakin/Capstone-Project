package home.oleg.placesnearme.feature_venues_history;

import home.oleg.placenearme.interactors.EvaluateDistance;
import home.oleg.placenearme.interactors.GetAllHistory;
import home.oleg.placesnearme.core_presentation.base.BaseViewModel;
import home.oleg.placesnearme.core_presentation.recyclerview.VenueViewItem;
import home.oleg.placesnearme.core_presentation.viewdata.VenueViewData;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by Oleg Sheliakin on 04.09.2018.
 * Contact me by email - olegsheliakin@gmail.com
 */
public class VenuesHistoryViewModel extends BaseViewModel<VenuesHistoryView> {

    private final GetAllHistory getAllHistory;
    private final EvaluateDistance evaluateDistance;

    public VenuesHistoryViewModel(GetAllHistory getAllHistory, EvaluateDistance evaluateDistance) {
        this.getAllHistory = getAllHistory;
        this.evaluateDistance = evaluateDistance;
    }

    @Override
    protected void onObserverCreated() {
        super.onObserverCreated();
        addToDisposables(
                getAllHistory.getAllHistory()
                        .flatMapSingle(evaluateDistance::evaluateDistance)
                        .map(VenueViewData::mapFrom)
                        .map(VenueViewItem::map)
                        .subscribeOn(Schedulers.computation())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(venues -> {
                            if(venues.isEmpty()) {
                                setState(VenuesHistoryView::showEmpty);
                            } else {
                                setState(venuesHistoryView -> venuesHistoryView.showData(venues));
                            }
                        }, Throwable::printStackTrace)
        );
    }
}
