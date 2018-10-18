package com.smedialink.feature_venue_detail.venue.view;

import android.arch.lifecycle.LifecycleOwner;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.widget.NestedScrollView;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.ImageView;

import com.smedialink.common.Optional;
import com.smedialink.feature_venue_detail.R;
import com.smedialink.feature_venue_detail.venue.viewmodel.VenueViewModel;

import javax.inject.Inject;

import home.oleg.coordinator_behavior.GoogleMapsBottomSheetBehavior;
import home.oleg.coordinator_behavior.MergedAppBarLayout;
import home.oleg.coordinator_behavior.MergedAppBarLayoutBehavior;
import home.oleg.placesnearme.core_presentation.utils.ImageLoader;
import home.oleg.placesnearme.core_presentation.view_actions.ViewActionObserver;
import home.oleg.placesnearme.core_presentation.viewdata.PhotoViewData;
import home.oleg.placesnearme.core_presentation.viewdata.ShortVenueViewData;
import home.oleg.placesnearme.core_presentation.viewdata.VenueViewData;

/**
 * Created by Oleg Sheliakin on 09.10.2018.
 * Contact me by email - olegsheliakin@gmail.com
 */
public class VenueViewFacade implements VenueView {

    private final LifecycleOwner lifecycleOwner;
    private final VenueViewModel venueViewModel;
    private VenueDetailsView venueDetailsView;

    private ImageView ivVenuePhoto;
    private GoogleMapsBottomSheetBehavior behavior;

    private FloatingActionButton fabFavorite;

    @Inject
    public VenueViewFacade(LifecycleOwner lifecycleOwner,
                           VenueViewModel venueViewModel) {
        this.lifecycleOwner = lifecycleOwner;
        this.venueViewModel = venueViewModel;
    }

    public void onCreateView(View view) {
        venueDetailsView = view.findViewById(R.id.venueView);
        fabFavorite = view.findViewById(R.id.fabFavoriteButton);
        ivVenuePhoto = view.findViewById(R.id.ivVenuePhoto);

        initBehavior(view);

        fabFavorite.setOnClickListener(v -> venueViewModel.favoriteClicked());

        venueViewModel.getObserver()
                .observe(lifecycleOwner, ViewActionObserver.create(this));
    }

    public void setVenue(ShortVenueViewData venueMapViewData) {
        Optional.of(venueDetailsView).ifPresent(VenueDetailsView::clearContent);
        venueViewModel.setVenue(venueMapViewData);
    }

    @Override
    public void show(VenueViewData venue) {
        String url = Optional.of(venue.getBestPhoto())
                .map(PhotoViewData::getFullSizeUrl)
                .getOrNull();

        ImageLoader.loadImage(ivVenuePhoto, url);

        fabFavorite.setSelected(venue.isFavorite());

        venueDetailsView.show(venue);
    }

    @Override
    public void showShortVenue(ShortVenueViewData venue) {
        venueDetailsView.showShortVenue(venue);
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
        MergedAppBarLayoutBehavior mergedAppBarLayoutBehavior = MergedAppBarLayoutBehavior.from(mergedAppBarLayout);
        mergedAppBarLayoutBehavior.setToolbarTitle("Title Dummy");

        nestedScrollView.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                CoordinatorLayout.LayoutParams layoutParams = new CoordinatorLayout.LayoutParams(ivVenuePhoto.getMeasuredWidth(), behavior.getHeaderHeight());
                ivVenuePhoto.setLayoutParams(layoutParams);
                nestedScrollView.getViewTreeObserver().removeOnGlobalLayoutListener(this);
            }
        });
    }
}
