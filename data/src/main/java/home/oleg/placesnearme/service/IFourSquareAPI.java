package home.oleg.placesnearme.service;


import java.util.Map;

import home.oleg.placesnearme.models.responses.ExploreResponse;
import home.oleg.placesnearme.models.responses.PhotosResponse;
import home.oleg.placesnearme.models.responses.Response;
import home.oleg.placesnearme.models.responses.TipsResponse;
import home.oleg.placesnearme.models.responses.VenueDetailResponse;
import home.oleg.placesnearme.models.responses.VenuesResponse;
import io.reactivex.Observable;
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

    @GET("v2/venues/{id}/tips")
    Single<Response<TipsResponse>> getTips(
            @Path("id") String id,
            @Query("offset") int offset,
            @Query("limit") int limit,
            @Query("sort") String sort);

    @GET("v2/venues/{id}")
    Single<Response<VenueDetailResponse>> getDetail(@Path("id") String id);

    @GET("v2/venues/{id}/photos")
    Single<Response<PhotosResponse>> getPhotos(
            @Path("id") String id,
            @Query("offset") int offset,
            @Query("limit") int limit);

}
