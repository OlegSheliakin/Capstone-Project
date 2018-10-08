package home.oleg.placesnearme.network.service;


import home.oleg.placesnearme.network.models.reposnses.ExploreResponse;
import home.oleg.placesnearme.network.models.reposnses.PhotosResponse;
import home.oleg.placesnearme.network.models.reposnses.Response;
import home.oleg.placesnearme.network.models.reposnses.VenueDetailResponse;

import java.util.Map;

import home.oleg.placesnearme.network.models.reposnses.VenuesResponse;
import io.reactivex.Single;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;
import retrofit2.http.QueryMap;

/**
 * Created by Oleg on 10.08.2018.
 */
public interface IFourSquareAPI {

    @GET("v2/venues/explore")
    Single<Response<ExploreResponse>> explore(@QueryMap Map<String, String> queryMap);

    @GET("v2/venues/search")
    Single<Response<VenuesResponse>> search(@QueryMap Map<String, String> queryMap);

    @GET("v2/venues/{id}")
    Single<Response<VenueDetailResponse>> getDetail(@Path("id") String id);

    @GET("v2/venues/{id}/photos")
    Single<Response<PhotosResponse>> getPhotos(
            @Path("id") String id,
            @Query("offset") int offset,
            @Query("limit") int limit);

}
