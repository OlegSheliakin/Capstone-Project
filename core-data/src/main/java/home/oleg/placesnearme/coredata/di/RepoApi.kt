package home.oleg.placesnearme.coredata.di

import android.content.Context
import com.smedialink.common.util.SingletonHolder
import home.oleg.placesnearme.coredi.AppApiProvider
import home.oleg.placesnearme.coredomain.repositories.DetailedVenueRepository
import home.oleg.placesnearme.coredomain.repositories.DistanceRepository
import home.oleg.placesnearme.coredomain.repositories.FavoriteVenuesRepository
import home.oleg.placesnearme.coredomain.repositories.SectionRepository
import home.oleg.placesnearme.coredomain.repositories.UserLocationRepository
import home.oleg.placesnearme.coredomain.repositories.VenueHistoryRepository
import home.oleg.placesnearme.coredomain.repositories.VenueRepository

interface RepoApi {

    fun sectionRepo(): SectionRepository

    fun detailedRepo(): DetailedVenueRepository

    fun distanceRepo(): DistanceRepository

    fun favoriteVenuesRepo(): FavoriteVenuesRepository

    fun userLocationRepo(): UserLocationRepository

    fun venueHistoryRepo(): VenueHistoryRepository

    fun venueRepo(): VenueRepository

    companion object : SingletonHolder<RepoApi, Context>(creator = {
        val appApi = (it as AppApiProvider).appApi
        DaggerRepoComponent.builder()
                .appApi(appApi)
                .build()
    })
}