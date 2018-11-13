package com.smedialink.feature_venue_detail.domain

import home.oleg.placenearme.interactors.EvaluateDistance
import home.oleg.placenearme.models.DetailedVenue
import home.oleg.placenearme.repositories.DetailedVenueRepository
import home.oleg.placenearme.repositories.VenueHistoryRepository
import io.reactivex.Flowable
import io.reactivex.functions.BiFunction
import org.intellij.lang.annotations.Flow
import javax.inject.Inject

class GetDetailedVenue @Inject constructor(
        private val evaluateDistance: EvaluateDistance,
        private val detailedVenueRepository: DetailedVenueRepository,
        private val venueHistoryRepository: VenueHistoryRepository) {

    operator fun invoke(id: String, type: Type) : Flowable<DetailedVenue> {
        return when(type){
            Type.STREAM -> get(id, detailedVenueRepository.stream(id))
            Type.FETCH -> get(id, detailedVenueRepository.getDetailedVenueById(id))
        }.flatMapSingle(evaluateDistance::evaluateDistance)
    }

    private fun get(id: String, detailedVenueFlowable: Flowable<DetailedVenue>): Flowable<DetailedVenue> {
        return Flowable.combineLatest(venueHistoryRepository.isHereNow(id), detailedVenueFlowable,
                BiFunction { b, v ->
                    v.copy(isHereNow = b)
                })
    }

    enum class Type {
        STREAM, FETCH
    }

}
