package home.oleg.placesnearme.network.service


import home.oleg.placesnearme.network.models.reposnses.ExploreResponse
import home.oleg.placesnearme.network.models.reposnses.PhotosResponse
import home.oleg.placesnearme.network.models.reposnses.Response
import home.oleg.placesnearme.network.models.reposnses.VenueDetailResponse
import home.oleg.placesnearme.network.models.reposnses.VenuesResponse
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import retrofit2.http.QueryMap

/**
 * Created by Oleg on 10.08.2018.
 */
interface IFourSquareAPI {

    @GET("v2/venues/explore")
    fun explore(@QueryMap queryMap: Map<String, String>): Single<Response<ExploreResponse>>

    @GET("v2/venues/search")
    fun search(@QueryMap queryMap: Map<String, String>): Single<Response<VenuesResponse>>

    @GET("v2/venues/{id}")
    fun getDetail(@Path("id") id: String): Single<Response<VenueDetailResponse>>

    @GET("v2/venues/{id}/photos")
    fun getPhotos(
            @Path("id") id: String,
            @Query("offset") offset: Int,
            @Query("limit") limit: Int): Single<Response<PhotosResponse>>

}
