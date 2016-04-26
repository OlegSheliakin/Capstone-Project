package home.oleg.placesnearme;


import java.util.Map;

import home.oleg.placesnearme.models.FullResponse;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.QueryMap;

/**
 * Created by Oleg on 16.04.2016.
 */
public interface IFourSquareAPI {

    @GET("v2/venues/explore?")
    Call<FullResponse> getItems(@QueryMap Map<String, String> queryMap);
}
