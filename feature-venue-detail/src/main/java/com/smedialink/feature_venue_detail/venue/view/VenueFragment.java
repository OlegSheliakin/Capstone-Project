package com.smedialink.feature_venue_detail.venue.view;

import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.smedialink.feature_venue_detail.R;
import com.smedialink.feature_venue_detail.venue.viewmodel.VenueViewModel;

import javax.inject.Inject;

import home.oleg.placesnearme.core_presentation.viewdata.ShortVenueViewData;
import home.oleg.placesnearme.core_presentation.viewdata.VenueViewData;

public class VenueFragment extends Fragment implements VenueView {

    private static String KEY_VENUE = "key_venue";

    @Inject
    VenueViewModel venueViewModel;

    private VenueDetailsView venueDetailsView;

    public static void show(
            @NonNull FragmentManager fragmentManager,
            @IdRes int containerId,
            @NonNull ShortVenueViewData venueMapViewData) {
        Bundle bundle = new Bundle();
        bundle.putParcelable(KEY_VENUE, venueMapViewData);

        VenueFragment fragment = (VenueFragment) fragmentManager.findFragmentById(containerId);
        if (fragment != null) {
            fragment.setArguments(bundle);
            fragment.updateVenue();
        } else {
            VenueFragment venueFragment = new VenueFragment();
            venueFragment.setArguments(bundle);

            fragmentManager
                    .beginTransaction()
                    .add(containerId, venueFragment)
                    .commit();
        }
    }

    public void updateVenue() {
        assert getArguments() != null;
        ShortVenueViewData venueMapViewData = getArguments().getParcelable(KEY_VENUE);
        venueViewModel.setVenue(venueMapViewData);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_venue, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        venueDetailsView = view.findViewById(R.id.venueView);
        updateVenue();
    }

    @Override
    public void show(VenueViewData venue) {
        venueDetailsView.show(venue);
    }

    @Override
    public void showShortVenue(ShortVenueViewData venue) {
        venueDetailsView.showShortVenue(venue);
    }

    @Override
    public void showError() {

    }

    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

    }
}
