package home.oleg.placesnearme.feature_map.presentation.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import home.oleg.placenearme.models.UserLocation
import home.oleg.placesnearme.core_presentation.delegate.disposableDelegate
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.schedulers.Schedulers

/**
 * Created by Oleg Sheliakin on 17.09.2018.
 * Contact me by email - olegsheliakin@gmail.com
 */
class UserLocationViewModel(private val getLocation: () -> Single<UserLocation>) : ViewModel() {

    private var disposable: Disposable? by disposableDelegate()
    private val stateInternal = MutableLiveData<UserLocation>()

    val location = stateInternal

    fun requestUserLocation() {
        disposable = getLocation()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeBy(
                        onSuccess = { stateInternal.setValue(it) },
                        onError = Throwable::printStackTrace)
    }
}
