package home.oleg.feature_add_history;

import android.support.annotation.NonNull;

import com.smedialink.common.function.Action;

import home.oleg.feature_add_history.interactor.CheckInOut;
import home.oleg.feature_add_history.view.CheckInOutView;
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
        if (venueViewData == null) {
            setState(checkInOutView -> {

            });
            return;
        }

        addToDisposables(
                Single.fromCallable(venueViewData::mapToDetailVenue)
                        .flatMap(checkInOut::execute)
                        .subscribeOn(Schedulers.computation())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(
                                isChecked -> {
                                    if (isChecked) {
                                        setState(CheckInOutView::checkedIn);
                                    } else {
                                        setState(CheckInOutView::checkedOut);
                                    }
                                },
                                Throwable::printStackTrace)
        );
    }
}
