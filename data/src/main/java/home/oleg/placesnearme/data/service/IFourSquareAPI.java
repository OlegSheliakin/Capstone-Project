package home.oleg.placesnearme.data.service;


import java.util.Map;

import home.oleg.placenearme.domain.models.FullResponse;
import io.reactivex.Observable;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.QueryMap;

/**
 * Created by Oleg on 16.04.2016.
 */
public interface IFourSquareAPI {

    @GET("v2/venues/explore?")
    Observable<FullResponse> getItems(@QueryMap Map<String, String> queryMap);

}
