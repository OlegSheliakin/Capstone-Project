package home.oleg.placesnearme.feature_venues_history;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.smedialink.common.Optional;
import com.smedialink.feature_add_favorite.CreateFavoriteView;
import com.smedialink.feature_add_favorite.CreateFavoriteViewModel;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import home.oleg.placesnearme.core_presentation.recyclerview.VenueViewItem;
import home.oleg.placesnearme.core_presentation.view_actions.ViewActionObserver;
import home.oleg.placesnearme.core_presentation.viewdata.VenueViewData;
import home.oleg.placesnearme.feature_venues_history.di.VenueHistoryComponent;

public class VenuesHistoryFragment extends Fragment
        implements HistoryVenuesAdapter.FavoriteClicksListener, VenuesHistoryView, CreateFavoriteView {

    @Inject
    HistoryVenuesAdapter historyVenuesAdapter;

    @Inject
    VenuesHistoryViewModel placesHistoryViewModel;

    @Inject
    CreateFavoriteViewModel createFavoriteViewModel;

    public VenuesHistoryFragment() {
        // Required empty public constructor
    }

    @Override
    public void onAttach(Context context) {
        injectDependencies(context);
        super.onAttach(context);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_places_history, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        RecyclerView rvHistoryVenues = view.findViewById(R.id.rvHistoryVenues);

        rvHistoryVenues.setLayoutManager(new LinearLayoutManager(getContext()));
        rvHistoryVenues.setAdapter(historyVenuesAdapter);

        Toolbar toolbar = view.findViewById(R.id.toolbar);
        Optional.of((AppCompatActivity) getActivity())
                .ifPresent(appCompatActivity -> {
                    appCompatActivity.setSupportActionBar(toolbar);
                    appCompatActivity.getSupportActionBar().setTitle(R.string.history_title);
                });

        placesHistoryViewModel.getObserver().observe(this, ViewActionObserver.create(this));
        createFavoriteViewModel.getObserver().observe(this, ViewActionObserver.create(this));
    }

    private void injectDependencies(Context context) {
        VenueHistoryComponent.Injector.inject(this);
    }

    @Override
    public void favoriteClicked(VenueViewData venueViewData) {
        createFavoriteViewModel.manageFavorite(venueViewData);
    }

    @Override
    public void showOnMapClicked(VenueViewData venueViewData) {

    }

    @Override
    public void showData(List<VenueViewItem> venueViewItemList) {
        historyVenuesAdapter.submitList(new ArrayList<>(venueViewItemList));
    }

    @Override
    public void showEmpty() {
        historyVenuesAdapter.showEmpty();
    }

    @Override
    public void favoriteAdded() {

    }

    @Override
    public void favoriteRemoved() {

    }
}
