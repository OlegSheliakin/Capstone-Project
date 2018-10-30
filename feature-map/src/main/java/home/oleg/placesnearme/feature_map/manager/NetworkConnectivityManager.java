package home.oleg.placesnearme.feature_map.manager;

import android.os.AsyncTask;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketAddress;

import javax.inject.Inject;

/**
 * Created by Oleg Sheliakin on 30.10.2018.
 * Contact me by email - olegsheliakin@gmail.com
 */
public class NetworkConnectivityManager {

    private ConnectivityTask connectivityTaks;

    @Inject
    public NetworkConnectivityManager() {

    }

    public void checkConnection(Callback callback) {
        if (connectivityTaks != null) {
            connectivityTaks.cancel(true);
        }
        connectivityTaks = new ConnectivityTask(callback);
        connectivityTaks.execute();
    }

    public interface Callback {
        void connectionAvailable();

        void connectionNotAvailable();
    }

    static class ConnectivityTask extends AsyncTask<Void, Void, Boolean> {

        private static final int TIMEOUT_MS = 1500;
        private static final int PORT = 53;

        private final NetworkConnectivityManager.Callback callback;

        ConnectivityTask(NetworkConnectivityManager.Callback callback) {
            this.callback = callback;
        }

        @Override
        protected Boolean doInBackground(Void... voids) {
            try {
                Socket sock = new Socket();
                SocketAddress sockaddr = new InetSocketAddress("8.8.8.8", PORT);

                sock.connect(sockaddr, TIMEOUT_MS);
                sock.close();

                return true;
            } catch (IOException e) {
                return false;
            }
        }

        @Override
        protected void onPostExecute(Boolean aBoolean) {
            super.onPostExecute(aBoolean);
            if (aBoolean) {
                callback.connectionAvailable();
            } else {
                callback.connectionNotAvailable();
            }
        }
    }
}
