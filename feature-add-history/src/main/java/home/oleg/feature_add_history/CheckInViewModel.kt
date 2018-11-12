package home.oleg.feature_add_history

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.smedialink.feature_add_history.R
import home.oleg.feature_add_history.interactor.CheckInOut
import home.oleg.placesnearme.core_presentation.base.MessageEvent
import home.oleg.placesnearme.core_presentation.provider.ResourceProvider
import home.oleg.placesnearme.core_presentation.viewdata.VenueViewData
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

/**
 * Created by Oleg Sheliakin on 23.10.2018.
 * Contact me by email - olegsheliakin@gmail.com
 */
class CheckInViewModel(
        private val checkInOut: CheckInOut,
        private val resourceProvider: ResourceProvider) : ViewModel() {

    val state = MutableLiveData<MessageEvent>()

    private var disposable: Disposable? = null

    fun manage(venueViewData: VenueViewData?) {
        if (venueViewData == null) {
            return
        }

        if (disposable != null && disposable?.isDisposed != true) {
            disposable?.dispose()
        }

        disposable = Single.fromCallable { venueViewData.mapToDetailVenue() }
                .flatMap<Boolean> { checkInOut.execute(it) }
                .subscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        { isChecked ->
                            val stringsRes: Int = if (isChecked!!) {
                                R.string.add_history_message_success_added
                            } else {
                                R.string.add_history_message_success_removed
                            }
                            val messageEvent = MessageEvent(resourceProvider.getString(stringsRes))
                            state.setValue(messageEvent)
                        }, { it.printStackTrace() })
    }
}
