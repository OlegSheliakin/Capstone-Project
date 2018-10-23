package home.oleg.feature_add_history;

import home.oleg.placenearme.interactors.CheckInOut;
import home.oleg.placesnearme.core_presentation.base.BaseViewModel;
import home.oleg.placesnearme.core_presentation.viewdata.VenueViewData;
import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by Oleg Sheliakin on 23.10.2018.
 * Contact me by email - olegsheliakin@gmail.com
 */
public class CheckInViewModel extends BaseViewModel<CheckInOutView> {

    private final CheckInOut checkInOut;

    public CheckInViewModel(CheckInOut checkInOut) {
        this.checkInOut = checkInOut;
    }

    public void manage(VenueViewData venueViewData) {
        addToDisposables(
                Single.fromCallable(venueViewData::mapToDetailVenue)
                        .flatMapCompletable(checkInOut::execute)
                        .subscribeOn(Schedulers.computation())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(
                                () -> setAction(CheckInOutView::checkedIn),
                                Throwable::printStackTrace)
        );
    }
}
