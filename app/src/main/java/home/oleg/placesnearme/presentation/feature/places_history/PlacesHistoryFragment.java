package home.oleg.placesnearme.presentation.feature.places_history;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import home.oleg.placesnearme.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class PlacesHistoryFragment extends Fragment {


    public PlacesHistoryFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_places_history, container, false);
    }

}
