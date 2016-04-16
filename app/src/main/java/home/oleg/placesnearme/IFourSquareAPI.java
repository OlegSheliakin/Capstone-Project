package home.oleg.placesnearme;


import java.util.Map;

import home.oleg.placesnearme.retrofit_models.Model;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.QueryMap;


/**
 * Created by Oleg on 16.04.2016.
 */
public interface IFourSquareAPI {

    @GET("v2/venues/explore?")
    public Call<Model> getItems(@QueryMap Map<String, String> queryMap);
}
