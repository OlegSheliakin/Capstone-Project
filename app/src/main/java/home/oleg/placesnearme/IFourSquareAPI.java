package home.oleg.placesnearme;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import home.oleg.placesnearme.models.Item;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;
import retrofit2.http.QueryMap;

/**
 * Created by Oleg on 16.04.2016.
 */
public interface IFourSquareAPI {

    @GET("venues/explore?ll=47.20,38.96&radius=1000&section=food&client_id=BMDK0DP0YBJCG4BTIIC4SOEA2MMT2U4UZLJSBBZY0X2A23GF&client_secret=MMSC3RSUOFRTQO13LCB5RY0P0WOQ14M0X1GCIUT32GM4D3YN&v=20160412")
    public Response getItems();
}
