package home.oleg.placesnearme;

import android.location.Location;
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

import home.oleg.placesnearme.models.Item;

/**
 * Created by Oleg on 10.04.2016.
 */
public class VenuesNearMe extends AsyncTask<String, Void, ArrayList<Item>> {

    private GoogleMap map;
    private ArrayList<Item> items;
    private int radius;
    private Location location;

    public VenuesNearMe(GoogleMap map, int radius, Location location) {

        this.map = map;
        this.radius = radius;
        this.location = location;

    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        map.clear();
    }

    @Override
    protected ArrayList<Item> doInBackground(String... params) {
        items = new ArrayList<>();
        JSONObject jsonResponse = null;
        try {

            jsonResponse = executeRequest(getUri());

            int returnCode = Integer.parseInt(jsonResponse.getJSONObject("meta")
                    .getString("code"));

            if (returnCode == 200) {

                JSONArray jsonItemsArray = jsonResponse.getJSONObject("response")
                        .getJSONArray("groups")
                        .getJSONObject(0) // recommended group
                        .getJSONArray("items");
                items = convertJSONtoObject(jsonItemsArray);

            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
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

    private String getUri (){
        String uri = "https://api.foursquare.com/v2/venues/explore"
                + "?ll="
                + location.getLatitude()
                + ","
                + location.getLongitude()
                + "&radius="
                + radius
                + "&section=topPicks"
                + "&alt="
                + location.getAltitude()
                + "&llAcc="
                + location.getAccuracy()
                + "&client_id="
                + Constants.CLIENT_ID
                + "&client_secret="
                + Constants.CLIENT_SECRET
                + "&v="
                + Constants.FOURSQAURE_API_VERSION;
        return uri;
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
