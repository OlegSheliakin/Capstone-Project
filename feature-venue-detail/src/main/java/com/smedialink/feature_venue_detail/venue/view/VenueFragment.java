package com.smedialink.feature_venue_detail.venue.view;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.NestedScrollView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.ImageView;

import com.smedialink.common.Optional;
import com.smedialink.feature_add_favorite.CreateFavoriteView;
import com.smedialink.feature_add_favorite.CreateFavoriteViewModel;
import com.smedialink.feature_venue_detail.R;
import com.smedialink.feature_venue_detail.venue.di.VenueDetailComponent;
import com.smedialink.feature_venue_detail.venue.viewmodel.VenueViewModel;

import javax.inject.Inject;

import home.oleg.coordinator_behavior.GoogleMapsBottomSheetBehavior;
import home.oleg.coordinator_behavior.MergedAppBarLayout;
import home.oleg.coordinator_behavior.MergedAppBarLayoutBehavior;
import home.oleg.feature_add_history.CheckInViewModel;
import home.oleg.feature_add_history.view.CheckInOutView;
import home.oleg.placesnearme.core_presentation.ShowHideBottomBarListener;
import home.oleg.placesnearme.core_presentation.delegate.ToastDelegate;
import home.oleg.placesnearme.core_presentation.utils.ImageLoader;
import home.oleg.placesnearme.core_presentation.view_actions.ViewActionObserver;
import home.oleg.placesnearme.core_presentation.viewdata.PhotoViewData;
import home.oleg.placesnearme.core_presentation.viewdata.PreviewVenueViewData;
import home.oleg.placesnearme.core_presentation.viewdata.VenueViewData;

public class VenueFragment extends Fragment implements VenueView, CreateFavoriteView, CheckInOutView {

    private static String KEY_VENUE_ID = "key_venue_id";

    private VenueDetailsView venueDetailsView;
    private ImageView ivVenuePhoto;
    private FloatingActionButton favoriteButton;
    private FloatingActionButton fabCheckInButton;
    private MergedAppBarLayoutBehavior mergedAppBarLayoutBehavior;
    private GoogleMapsBottomSheetBehavior behavior;

    @Inject
    VenueViewModel venueViewModel;

    @Inject
    CreateFavoriteViewModel createFavoriteViewModel;

    @Inject
    CheckInViewModel checkInViewModel;

    @Inject
    ToastDelegate toastDelegate;

    @Override
    public void onAttach(Context context) {
        injectDependencies();
        super.onAttach(context);
    }

    private void injectDependencies() {
        VenueDetailComponent.Injector.inject(this);
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
        ivVenuePhoto = view.findViewById(R.id.ivVenuePhoto);
        initBehavior(view);

        fabCheckInButton = view.findViewById(R.id.fabCheckInButton);
        fabCheckInButton.setOnClickListener(v -> checkInViewModel.manage(venueViewModel.getVenueViewData()));
        favoriteButton = view.findViewById(R.id.fabFavoriteButton);
        favoriteButton.setOnClickListener(v -> createFavoriteViewModel.manageFavorite(venueViewModel.getVenueViewData()));

        venueViewModel.getObserver().observe(this, ViewActionObserver.create(this));
        createFavoriteViewModel.getObserver().observe(this, ViewActionObserver.create(this));
        checkInViewModel.getObserver().observe(this, ViewActionObserver.create(this));

        Bundle bundle = getArguments();
        if (bundle != null && bundle.containsKey(KEY_VENUE_ID)) {
            String venueId = bundle.getString(KEY_VENUE_ID);
            venueViewModel.load(venueId);
        }
    }

    public void showVenue(String venueId) {
        Bundle bundle = new Bundle();
        bundle.putString(KEY_VENUE_ID, venueId);
        setArguments(bundle);
        behavior.setState(GoogleMapsBottomSheetBehavior.STATE_ANCHORED);

        venueViewModel.load(venueId);
    }

    @Override
    public void show(VenueViewData venue) {
        venueDetailsView.show(venue);

        String url = Optional.of(venue.getBestPhoto())
                .map(PhotoViewData::getFullSizeUrl)
                .getOrNull();

        ImageLoader.loadImage(ivVenuePhoto, url);

        mergedAppBarLayoutBehavior.setToolbarTitle(venue.getTitle());
        favoriteButton.setSelected(venue.isFavorite());
        fabCheckInButton.setSelected(venue.isHere());
    }

    @Override
    public void showError() {
        toastDelegate.showError("Something goes wrong");
    }

    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

    }

    private void initBehavior(View view) {
        NestedScrollView nestedScrollView = view.findViewById(R.id.nestedScrollView);
        toastDelegate.attach(view.getContext());

        behavior = GoogleMapsBottomSheetBehavior.from(nestedScrollView);
        behavior.setParallax(ivVenuePhoto);
        behavior.setSkipCollapsed(true);

        MergedAppBarLayout mergedAppBarLayout = view.findViewById(R.id.mergedappbarlayout);
        mergedAppBarLayoutBehavior = MergedAppBarLayoutBehavior.from(mergedAppBarLayout);
    }

    @Override
    public void favoriteAdded() {
        toastDelegate.showSuccess("Added to favorites");
    }

    @Override
    public void favoriteRemoved() {
        toastDelegate.showSuccess("Removed to favorites");
    }

    @Override
    public void checkedIn() {
        toastDelegate.showSuccess("You have checked in");
    }

    @Override
    public void checkedOut() {
        toastDelegate.showSuccess("You have checked out");
    }
}
