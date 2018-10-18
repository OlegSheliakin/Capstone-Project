package home.oleg.placesnearme.data.provider;

import android.annotation.SuppressLint;
import android.content.Context;
import android.location.Location;
import android.support.annotation.NonNull;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.tasks.Task;

import home.oleg.placenearme.exception.LocationEmptyException;
import io.reactivex.Single;
import io.reactivex.SingleEmitter;
import io.reactivex.SingleOnSubscribe;

import static com.google.android.gms.location.LocationServices.getFusedLocationProviderClient;

/**
 * Created by Oleg Sheliakin on 17.09.2018.
 * Contact me by email - olegsheliakin@gmail.com
 */
public final class ReactiveLocationStore implements LocationStore {

    private final FusedLocationProviderClient fusedLocationProviderClient;

    private ReactiveLocationStore(@NonNull FusedLocationProviderClient fusedLocationProviderClient) {
        this.fusedLocationProviderClient = fusedLocationProviderClient;
    }

    public static ReactiveLocationStore create(Context context) {
        FusedLocationProviderClient fsProvider = getFusedLocationProviderClient(context);
        return new ReactiveLocationStore(fsProvider);
    }

    @Override
    public Single<Location> getLastLocation() {
        return Single.create(new ReactiveLocationStore.LocationSingleSubscriber());
    }

    private class LocationSingleSubscriber implements SingleOnSubscribe<Location> {

        @SuppressLint("MissingPermission")
        @Override
        public void subscribe(SingleEmitter<Location> e) {
            Task<Location> task = fusedLocationProviderClient.getLastLocation();
            task.addOnSuccessListener(location -> {
                if (location == null) {
                    e.onError(new LocationEmptyException());
                } else {
                    e.onSuccess(location);
                }
            });
            task.addOnFailureListener(e::onError);
        }
    }

}
