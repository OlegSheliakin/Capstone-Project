package home.oleg.placesnearme;

import android.os.AsyncTask;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import home.oleg.placesnearme.models.Item;
import retrofit2.Response;
import retrofit2.Retrofit;

/**
 * Created by Oleg on 10.04.2016.
 */
public class VenuesNearMe extends AsyncTask<String, Void, ArrayList<Item>> {

    private GoogleMap map;
    private ArrayList<Item> items;
    private Parameters parameters;

    public VenuesNearMe(GoogleMap map, Parameters parameters) {
        this.map = map;
        this.parameters = parameters;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected ArrayList<Item> doInBackground(String... params) {
        items = new ArrayList<>();
        JSONObject jsonResponse = null;
/*
            String uri = new VenuesHttpRequest(parameters).toString();
            jsonResponse = executeRequest(uri);

            int returnCode = Integer.parseInt(jsonResponse.getJSONObject("meta")
                    .getString("code"));

            if (returnCode == 200) {

                JSONArray jsonItemsArray = jsonResponse.getJSONObject("response")
                        .getJSONArray("groups")
                        .getJSONObject(0) // recommended group
                        .getJSONArray("items");
                items = convertJSONtoObject(jsonItemsArray);
                }*/
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://api.foursquare.com/v2/")
                .build();

        IFourSquareAPI restItems = retrofit.create(IFourSquareAPI.class);
        Map<String, String> map = new HashMap<>();
        map.put("radius", String.valueOf(1000));
        map.put("section", parameters.getSection());
        map.put("client_id", Constants.CLIENT_ID);
        map.put("client_secret", Constants.CLIENT_SECRET);

        Response response = restItems.getItems();

        return items;
    }

    @Override
    protected void onPostExecute(ArrayList<Item> items) {
        super.onPostExecute(items);
        for (Item v : items) {
            map.addMarker(new MarkerOptions()
                    .title(v.getVenue().getLocation().getAddress() + " " + v.getVenue().getName()).position
                            (new LatLng(v.getVenue().getLocation().getLat(),
                                    v.getVenue().getLocation().getLng())));
        }
    }

    private JSONObject executeRequest(String path) throws IOException, JSONException {
        String response = null;
        InputStream in = null;
        try {
            URL url = new URL(path);
            HttpURLConnection c = (HttpURLConnection) url.openConnection();
            c.setRequestMethod("GET");
            c.setReadTimeout(10000);
            c.connect();

            in = new BufferedInputStream(c.getInputStream());
            response = readStream(in);
        } finally {
            in.close();
        }
        return new JSONObject(response);
    }

    private String readStream(InputStream in) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(in));
        StringBuilder buf = new StringBuilder();
        String line = null;
        try {
            while ((line = reader.readLine()) != null) {
                buf.append(line + "\n");
            }
        } finally {
            reader.close();
        }
        return (buf.toString());
    }

    private ArrayList<Item> convertJSONtoObject(JSONArray jsonItemsArray) throws JSONException {
        Gson gson = new Gson();
        ArrayList<Item> items = new ArrayList<>();
        for (int i = 0; i < jsonItemsArray.length(); i++) {
            Item item = gson.fromJson(jsonItemsArray.getJSONObject(i).toString(), Item.class);
            items.add(i, item);
        }
        return items;
    }
}
