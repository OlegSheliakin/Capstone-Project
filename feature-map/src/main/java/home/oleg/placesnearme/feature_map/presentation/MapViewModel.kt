package home.oleg.placesnearme.feature_map.presentation

import com.smedialink.common.base.BaseViewModel
import com.smedialink.common.ext.reduceState
import com.smedialink.common.propertydelegate.disposableDelegate
import home.oleg.placesnearme.coredomain.interactors.GetRecommendedVenues
import home.oleg.placesnearme.coredomain.models.Section
import home.oleg.placesnearme.coredomain.repositories.UserLatLngRepository
import home.oleg.placesnearme.corepresentation.mapper.VenueMapViewMapper
import home.oleg.placesnearme.corepresentation.viewdata.PreviewPlace
import home.oleg.placesnearme.corettools.error_handler.ErrorHandler
import home.oleg.placesnearme.feature_map.presentation.MapViewState
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.schedulers.Schedulers

/**
 * Created by Oleg on 10.08.2018.
 */
class MapViewModel(private val errorHandler: ErrorHandler,
                   private val userLocationRepository: UserLatLngRepository,
                   private val getRecommendedVenues: GetRecommendedVenues)
    : BaseViewModel<MapViewState>() {

    private var searchDisposable: Disposable? by disposableDelegate()
    private var locationDisposable: Disposable? by disposableDelegate()

    val venuesHolder: MutableMap<String, PreviewPlace> = mutableMapOf()

    init {
        stateInternal.value = MapViewState.initial()
    }

    fun getRecommendedVenues(section: Section) {
        searchDisposable = getRecommendedVenues.getRecommendedSection(section)
                .map { sectionListPair -> VenueMapViewMapper.map(sectionListPair.first, sectionListPair.second) }
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe {
                    stateInternal.reduceState { state ->
                        state.copy(error = null, isVenuesLoading = true)
                    }
                }
                .subscribeBy(
                        onSuccess = { places ->
                            stateInternal.reduceState { state ->
                                state.copy(isVenuesLoading = false, places = places)
                            }
                        },
                        onError = { throwable ->
                            stateInternal.reduceState { state ->
                                state.copy(isVenuesLoading = false, error = errorHandler.handle(throwable))
                            }
                        })
    }

    fun requestUserLocation() {
        locationDisposable = userLocationRepository.latlng
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeBy(
                        onSuccess = { latlng ->
                            stateInternal.reduceState {
                                it.copy(userLatLng = latlng)
                            }
                        },
                        onError = {
                            stateInternal.reduceState { state ->
                                state.copy(isVenuesLoading = false, error = errorHandler.handle(it))
                            }
                        })
    }

    override fun onCleared() {
        super.onCleared()
        searchDisposable = null
        locationDisposable = null
    }

    fun openSearch() {
        stateInternal.reduceState {
            it.copy(isSearchShown = true)
        }
    }

    fun closeSearch() {
        stateInternal.reduceState {
            it.copy(isSearchShown = false)
        }
    }
}



