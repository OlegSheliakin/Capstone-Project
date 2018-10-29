package home.oleg.feature_add_history;

import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

import com.smedialink.feature_add_history.R;

import home.oleg.feature_add_history.interactor.CheckInOut;
import home.oleg.placesnearme.core_presentation.base.MessageEvent;
import home.oleg.placesnearme.core_presentation.provider.ResourceProvider;
import home.oleg.placesnearme.core_presentation.viewdata.VenueViewData;
import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by Oleg Sheliakin on 23.10.2018.
 * Contact me by email - olegsheliakin@gmail.com
 */
public class CheckInViewModel extends ViewModel {

    private final MutableLiveData<MessageEvent> state = new MutableLiveData<>();
    private final CheckInOut checkInOut;
    private final ResourceProvider resourceProvider;

    private Disposable disposable;

    public CheckInViewModel(CheckInOut checkInOut, ResourceProvider resourceProvider) {
        this.checkInOut = checkInOut;
        this.resourceProvider = resourceProvider;
    }

    public MutableLiveData<MessageEvent> getState() {
        return state;
    }

    public void manage(VenueViewData venueViewData) {
        if (venueViewData == null) {
            return;
        }

        if (disposable != null && !disposable.isDisposed()) {
            disposable.dispose();
        }

        disposable = Single.fromCallable(venueViewData::mapToDetailVenue)
                .flatMap(checkInOut::execute)
                .subscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        isChecked -> {
                            int stringsRes;
                            if (isChecked) {
                                stringsRes = R.string.add_history_message_success_added;
                            } else {
                                stringsRes = R.string.add_history_message_success_removed;
                            }
                            MessageEvent messageEvent = new MessageEvent(resourceProvider.getString(stringsRes));
                            state.setValue(messageEvent);
                        },
                        Throwable::printStackTrace);
    }
}
