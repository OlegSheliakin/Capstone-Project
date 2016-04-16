package home.oleg.placesnearme;

import android.os.AsyncTask;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import home.oleg.placesnearme.retrofit_models.FullResponse;
import home.oleg.placesnearme.retrofit_models.Item;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Oleg on 16.04.2016.
 */
class AsyncHttpRequest extends AsyncTask<String, Void, List<Item>> {

    private Parameters parameters;
    private MapActivity mapActivity;

    public AsyncHttpRequest(MapActivity mapActivity, Parameters parameters) {
        this.mapActivity = mapActivity;
        this.parameters = parameters;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected List<Item> doInBackground(String... params) {

        List<Item> items = new ArrayList<>();
        FullResponse fullResponse = null;

        Map<String,String> map = new HashMap<>();
        map.put(Constants.LL, parameters.getLocationLL());
        map.put(Constants.SECTION, parameters.getSection());
        map.put(Constants.RADIUS, parameters.getRadius());
        map.put(Constants.CLIENT_ID, parameters.getClientId());
        map.put(Constants.CLIENT_SECRET, parameters.getClientSecret());
        map.put(Constants.VERSION, parameters.getVersion());

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://api.foursquare.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        IFourSquareAPI restItems = retrofit.create(IFourSquareAPI.class);


        Call<FullResponse> call = restItems.getItems(map);
        try {
            Response<FullResponse> response = call.execute();
            fullResponse = response.body();
        } catch (IOException e) {
            e.printStackTrace();
        }

        if (fullResponse != null){
            items = fullResponse.getResponse().getGroups().get(0).getItems();
        }

        return items;
    }

    @Override
    protected void onPostExecute(List<Item> items) {
        super.onPostExecute(items);
        mapActivity.showVenues(items);
    }
}

