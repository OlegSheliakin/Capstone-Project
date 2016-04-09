package home.oleg.placesnearme;

import android.os.AsyncTask;
import android.util.Log;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by Oleg on 10.04.2016.
 */
public class VenuesNearMe extends AsyncTask<String, Void, String>{
    VenuesNearMe (){

    }
    @Override
    protected String doInBackground(String... params) {
        String content = null;
        try {
            String uri = "https://api.foursquare.com/v2/venues/explore"
                    + "?ll="
                    + 47.2012
                    + ","
                    + 38.9277
                    + "&radius="
                    + 100
                    + "&client_id="
                    + Constants.CLIENT_ID
                    + "&client_secret="
                    + Constants.CLIENT_SECRET
                    + "&v="
                    +  Constants.FOURSQAURE_API_VERSION;
            content = getResponse(uri);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return content;
    }

    @Override
    protected void onPostExecute(String content) {
        super.onPostExecute(content);
        Log.d("TAG", content);
    }

    private String getResponse(String path) throws IOException {
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
        return response;
    }

    private String readStream(InputStream in) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(in));
        StringBuilder buf = new StringBuilder();
        String line = null;
        try {
            while ((line = reader.readLine()) != null) {
                buf.append(line + "\n");
                buf.append("\n");
            }
        } finally {
            reader.close();
        }
        return (buf.toString());
    }
}
