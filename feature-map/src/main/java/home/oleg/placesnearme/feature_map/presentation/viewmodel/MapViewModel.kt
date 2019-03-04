package home.oleg.placesnearme.feature_map.presentation.viewmodel

import androidx.lifecycle.MutableLiveData
import com.smedialink.common.base.BaseViewModel
import com.smedialink.common.ext.reduceState
import com.smedialink.common.propertydelegate.disposableDelegate
import home.oleg.placesnearme.corepresentation.mapper.VenueMapViewMapper
import home.oleg.placesnearme.corepresentation.viewdata.PreviewVenueViewData
import home.oleg.placesnearme.coredomain.interactors.GetRecommendedVenues
import home.oleg.placesnearme.coredomain.models.Section
import home.oleg.placesnearme.coredomain.models.UserLocation
import home.oleg.placesnearme.coredomain.repositories.UserLocationRepository
import home.oleg.placesnearme.corettools.error_handler.ErrorHandler
import home.oleg.placesnearme.feature_map.presentation.state.MapViewState
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.schedulers.Schedulers

/**
 * Created by Oleg on 10.08.2018.
 */
class MapViewModel(
        private val errorHandler: ErrorHandler,
        private val userLocationRepository: UserLocationRepository,
        private val getRecommendedVenues: GetRecommendedVenues) : BaseViewModel() {

    private var searchDisposable: Disposable? by disposableDelegate()
    private var locationDisposable: Disposable? by disposableDelegate()

    private val viewStateInternal = MutableLiveData<MapViewState>()
    private val locationInternal = MutableLiveData<UserLocation>()
    private val venuesInternal = MutableLiveData<List<PreviewVenueViewData>>()

    val venuesHolder: MutableMap<String, PreviewVenueViewData> = mutableMapOf()

    val viewState = viewStateInternal
    val venues = venuesInternal
    val location = locationInternal

    init {
        viewStateInternal.value = MapViewState.initial()
    }

    fun getRecommendedVenues(section: Section) {
        searchDisposable = getRecommendedVenues.getRecommendedSection(section)
                .map { sectionListPair -> VenueMapViewMapper.map(sectionListPair.first, sectionListPair.second) }
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe {
                    viewStateInternal.reduceState { state ->
                        state.copy(error = null, isVenuesLoading = true)
                    }
                }
                .subscribeBy(
                        onSuccess = { venues ->
                            viewStateInternal.reduceState { state ->
                                state.copy(isVenuesLoading = false)
                            }

                            venuesInternal.setValue(venues)
                        },
                        onError = { throwable ->
                            viewStateInternal.reduceState { state ->
                                state.copy(isVenuesLoading = false, error = errorHandler.handle(throwable))
                            }
                        })
    }

    fun requestUserLocation() {
        locationDisposable = userLocationRepository.location
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeBy(
                        onSuccess = locationInternal::setValue,
                        onError = {
                            viewStateInternal.reduceState { state ->
                                state.copy(isVenuesLoading = false, error = errorHandler.handle(it))
                            }
                        })
    }

    override fun onCleared() {
        super.onCleared()
        searchDisposable = null
        locationDisposable == null
    }

    fun openSearch() {
        viewStateInternal.reduceState {
            it.copy(isSearchShown = true)
        }
    }

    fun closeSearch() {
        viewStateInternal.reduceState {
            it.copy(isSearchShown = false)
        }
    }

}
