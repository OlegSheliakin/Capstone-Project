package home.oleg.placesnearme.presentation.feature.map.view;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import javax.inject.Inject;

import home.oleg.placesnearme.presentation.feature.map.viewmodel.MapViewModel;
import home.oleg.placesnearme.presentation.viewobjects.VenueViewObject;
import io.reactivex.disposables.CompositeDisposable;

public class MapViewDelegate implements OnMapReadyCallback {

    private GoogleMap map;
    private final MapViewModel viewModel;

    @Inject
    public MapViewDelegate(MapViewModel viewModel) {
        this.viewModel = viewModel;
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        this.map = googleMap;
    }

    public void onCreate() {
        viewModel.observeResults()
                .subscribe(this::showVenues, Throwable::printStackTrace);
    }

    public void search(String query) {
        viewModel.search(query);
    }

    public void findByCategory(String category) {
        viewModel.getRecommended(category);
    }

    private void showVenues(Iterable<VenueViewObject> venues) {
        map.clear();

        for (VenueViewObject venue : venues) {
            LatLng latLng = new LatLng(venue.getLat(), venue.getLng());

            map.addMarker(new MarkerOptions()
                    .title(venue.getTitle())
                    .position(latLng));
        }

    }

}
