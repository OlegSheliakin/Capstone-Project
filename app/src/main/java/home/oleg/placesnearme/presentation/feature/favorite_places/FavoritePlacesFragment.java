package home.oleg.placesnearme.presentation.feature.favorite_places;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import home.oleg.placesnearme.R;

public class FavoritePlacesFragment extends Fragment {

    public FavoritePlacesFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_favorite_places, container, false);
    }

}
