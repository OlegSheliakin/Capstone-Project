package home.oleg.placesnearme.feature_map.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import home.oleg.placenearme.models.UserLocation
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

/**
 * Created by Oleg Sheliakin on 17.09.2018.
 * Contact me by email - olegsheliakin@gmail.com
 */
class UserLocationViewModel(private val getLocation: () -> Single<UserLocation>) : ViewModel() {

    private var disposable: Disposable? = null

    val state = MutableLiveData<UserLocation>()

    fun requestUserLocation() {
        disposable?.takeUnless { it.isDisposed }?.dispose()

        disposable = getLocation()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        { state.setValue(it) },
                        { _ -> })
    }
}
