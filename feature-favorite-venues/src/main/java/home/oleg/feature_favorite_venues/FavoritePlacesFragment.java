package home.oleg.feature_favorite_venues;

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
import com.smedialink.feature_add_favorite.CreateFavoriteViewModel;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import home.oleg.feature_favorite_venues.di.FavoriteVenuesComponent;
import home.oleg.placesnearme.core_presentation.ShowHideBottomBarListener;
import home.oleg.placesnearme.core_presentation.recyclerview.VenueViewItem;
import home.oleg.placesnearme.core_presentation.view_actions.ViewActionObserver;
import home.oleg.placesnearme.core_presentation.viewdata.VenueViewData;

public class FavoritePlacesFragment extends Fragment implements FavoriteVenuesAdapter.FavoriteClicksListener, FavoriteView {

    @Inject
    FavoritePlacesViewModel favoritePlacesViewModel;

    @Inject
    CreateFavoriteViewModel createFavoriteViewModel;

    @Inject
    FavoriteVenuesAdapter adapter;

    private ShowHideBottomBarListener showHideBottomBarListener;

    @Override
    public void onAttach(Context context) {
        injectDependencies();
        super.onAttach(context);
        if (context instanceof ShowHideBottomBarListener) {
            this.showHideBottomBarListener = (ShowHideBottomBarListener) context;
        }
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_favorite_places, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        RecyclerView rvFavorites = view.findViewById(R.id.rvFavoriteVenues);

        rvFavorites.setLayoutManager(new LinearLayoutManager(getContext()));
        rvFavorites.setAdapter(adapter);

        final Toolbar toolbar = view.findViewById(R.id.toolbar);
        Optional.of((AppCompatActivity) getActivity())
                .ifPresent(appCompatActivity -> {
                    appCompatActivity.setSupportActionBar(toolbar);
                    appCompatActivity.getSupportActionBar().setTitle(R.string.fragment_title_favorite);
                });

        favoritePlacesViewModel.getObserver().observe(this, ViewActionObserver.create(this));
    }

    @Override
    public void showData(List<VenueViewItem> detailedVenues) {
        adapter.submitList(new ArrayList<>(detailedVenues));
    }

    @Override
    public void showEmpty() {
        adapter.showEmpty();
    }

    @Override
    public void favoriteClicked(VenueViewData venueViewData) {
        createFavoriteViewModel.manageFavorite(venueViewData);
    }

    @Override
    public void onItemClicked(VenueViewData venueViewData) {
        showHideBottomBarListener.showVenueDetail(venueViewData);
    }

    private void injectDependencies() {
        FavoriteVenuesComponent.Injector.inject(this);
    }

}
