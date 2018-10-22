package com.smedialink.feature_venue_detail.venue.view;

import android.arch.lifecycle.LifecycleOwner;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.widget.NestedScrollView;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.ImageView;

import com.smedialink.common.Optional;
import com.smedialink.feature_add_favorite.CreateFavoriteView;
import com.smedialink.feature_add_favorite.CreateFavoriteViewModel;
import com.smedialink.feature_add_favorite.FavoriteButton;
import com.smedialink.feature_venue_detail.R;
import com.smedialink.feature_venue_detail.venue.viewmodel.VenueViewModel;

import javax.inject.Inject;

import home.oleg.coordinator_behavior.GoogleMapsBottomSheetBehavior;
import home.oleg.coordinator_behavior.MergedAppBarLayout;
import home.oleg.coordinator_behavior.MergedAppBarLayoutBehavior;
import home.oleg.placesnearme.core_presentation.utils.ImageLoader;
import home.oleg.placesnearme.core_presentation.view_actions.ViewActionObserver;
import home.oleg.placesnearme.core_presentation.viewdata.PhotoViewData;
import home.oleg.placesnearme.core_presentation.viewdata.PreviewVenueViewData;
import home.oleg.placesnearme.core_presentation.viewdata.VenueViewData;

/**
 * Created by Oleg Sheliakin on 09.10.2018.
 * Contact me by email - olegsheliakin@gmail.com
 */
public class VenueViewFacade implements VenueView, CreateFavoriteView {

    private final LifecycleOwner lifecycleOwner;
    private final VenueViewModel venueViewModel;
    private final CreateFavoriteViewModel createFavoriteViewModel;

    private VenueDetailsView venueDetailsView;
    private ImageView ivVenuePhoto;
    private FavoriteButton favoriteButton;
    private MergedAppBarLayoutBehavior mergedAppBarLayoutBehavior;
    private GoogleMapsBottomSheetBehavior behavior;

    @Inject
    public VenueViewFacade(
            LifecycleOwner lifecycleOwner,
            VenueViewModel venueViewModel,
            CreateFavoriteViewModel createFavoriteViewModel) {
        this.lifecycleOwner = lifecycleOwner;
        this.venueViewModel = venueViewModel;
        this.createFavoriteViewModel = createFavoriteViewModel;
    }

    public void onCreateView(View view) {
        venueDetailsView = view.findViewById(R.id.venueView);
        ivVenuePhoto = view.findViewById(R.id.ivVenuePhoto);

        favoriteButton = view.findViewById(R.id.fabFavoriteButton);
        favoriteButton.setOnClickListener(v -> createFavoriteViewModel.manageFavorite(venueViewModel.getVenueViewData()));

        initBehavior(view);

        venueViewModel.getObserver().observe(lifecycleOwner, ViewActionObserver.create(this));
        createFavoriteViewModel.getObserver().observe(lifecycleOwner, ViewActionObserver.create(this));
    }

    public void setVenue(PreviewVenueViewData venueMapViewData) {
        Optional.of(venueDetailsView).ifPresent(VenueDetailsView::clearContent);
        venueViewModel.setVenue(venueMapViewData);
    }

    @Override
    public void show(VenueViewData venue) {
        String url = Optional.of(venue.getBestPhoto())
                .map(PhotoViewData::getFullSizeUrl)
                .getOrNull();

        ImageLoader.loadImage(ivVenuePhoto, url);

        venueDetailsView.show(venue);
        favoriteButton.setSelected(venue.isFavorite());
    }

    @Override
    public void showPreviewVenue(PreviewVenueViewData venue) {
        venueDetailsView.clearContent();
        venueDetailsView.showPreview(venue);
        mergedAppBarLayoutBehavior.setToolbarTitle(venue.getTitle());
        behavior.setState(GoogleMapsBottomSheetBehavior.STATE_COLLAPSED);
    }

    @Override
    public void showError() {
        ImageLoader.loadImage(ivVenuePhoto, null);
    }

    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

    }

    private void initBehavior(View view) {
        NestedScrollView nestedScrollView = view.findViewById(R.id.nestedScrollView);

        behavior = GoogleMapsBottomSheetBehavior.from(nestedScrollView);
        behavior.setParallax(ivVenuePhoto);

        MergedAppBarLayout mergedAppBarLayout = view.findViewById(R.id.mergedappbarlayout);
        mergedAppBarLayoutBehavior = MergedAppBarLayoutBehavior.from(mergedAppBarLayout);

        nestedScrollView.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                CoordinatorLayout.LayoutParams layoutParams = new CoordinatorLayout.LayoutParams(ivVenuePhoto.getMeasuredWidth(), behavior.getHeaderHeight());
                ivVenuePhoto.setLayoutParams(layoutParams);
                nestedScrollView.getViewTreeObserver().removeOnGlobalLayoutListener(this);
            }
        });
    }

    @Override
    public void favoriteAdded() {
        //ignore
    }

    @Override
    public void favoriteRemoved() {
        //ignore
    }
}
