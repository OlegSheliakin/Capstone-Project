package com.smedialink.feature_venue_detail.venue.view;

import android.arch.lifecycle.LifecycleOwner;
import android.arch.lifecycle.Observer;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.widget.NestedScrollView;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.ImageView;

import com.smedialink.common.Optional;
import com.smedialink.feature_add_favorite.CreateFavoriteViewModel;
import com.smedialink.feature_venue_detail.R;
import com.smedialink.feature_venue_detail.state.VenueViewState;
import com.smedialink.feature_venue_detail.venue.viewmodel.VenueViewModel;

import javax.inject.Inject;

import home.oleg.coordinator_behavior.GoogleMapsBottomSheetBehavior;
import home.oleg.coordinator_behavior.MergedAppBarLayout;
import home.oleg.coordinator_behavior.MergedAppBarLayoutBehavior;
import home.oleg.feature_add_history.CheckInViewModel;
import home.oleg.placesnearme.core_presentation.ShowHideBottomBarListener;
import home.oleg.placesnearme.core_presentation.base.ErrorEvent;
import home.oleg.placesnearme.core_presentation.base.MessageEvent;
import home.oleg.placesnearme.core_presentation.delegate.ToastDelegate;
import home.oleg.placesnearme.core_presentation.utils.ImageLoader;
import home.oleg.placesnearme.core_presentation.viewdata.PhotoViewData;
import home.oleg.placesnearme.core_presentation.viewdata.PreviewVenueViewData;
import home.oleg.placesnearme.core_presentation.viewdata.VenueViewData;

/**
 * Created by Oleg Sheliakin on 09.10.2018.
 * Contact me by email - olegsheliakin@gmail.com
 */
public class VenueViewFacade implements Observer<MessageEvent> {

    private static final String KEY_STATE_BEHAVIOR = "key_state_behavior";
    private final LifecycleOwner lifecycleOwner;

    private final VenueViewModel venueViewModel;
    private final CreateFavoriteViewModel createFavoriteViewModel;
    private final CheckInViewModel checkInViewModel;
    private final ToastDelegate toastDelegate;
    private ShowHideBottomBarListener showHideBottomBarListener;

    private VenueDetailsView venueDetailsView;
    private ImageView ivVenuePhoto;
    private FloatingActionButton favoriteButton;
    private FloatingActionButton fabCheckInButton;
    private MergedAppBarLayoutBehavior mergedAppBarLayoutBehavior;
    private GoogleMapsBottomSheetBehavior behavior;
    private NestedScrollView nestedScrollView;

    private int behaviorState = GoogleMapsBottomSheetBehavior.STATE_HIDDEN;

    @Inject
    public VenueViewFacade(
            LifecycleOwner lifecycleOwner,
            VenueViewModel venueViewModel,
            CreateFavoriteViewModel createFavoriteViewModel,
            CheckInViewModel checkInViewModel, ToastDelegate toastDelegate) {
        this.lifecycleOwner = lifecycleOwner;
        this.venueViewModel = venueViewModel;
        this.createFavoriteViewModel = createFavoriteViewModel;
        this.checkInViewModel = checkInViewModel;
        this.toastDelegate = toastDelegate;
    }

    public void onCreateView(View view) {
        venueDetailsView = view.findViewById(R.id.venueView);
        ivVenuePhoto = view.findViewById(R.id.ivVenuePhoto);
        toastDelegate.attach(view.getContext());
        fabCheckInButton = view.findViewById(R.id.fabCheckInButton);
        fabCheckInButton.setOnClickListener(v -> checkInViewModel.manage(venueViewModel.getVenueViewData()));

        favoriteButton = view.findViewById(R.id.fabFavoriteButton);
        favoriteButton.setOnClickListener(v -> createFavoriteViewModel.manageFavorite(venueViewModel.getVenueViewData()));

        initBehavior(view);

        venueViewModel.getState().observe(lifecycleOwner, this::render);
        createFavoriteViewModel.getState().observe(lifecycleOwner, this);
        checkInViewModel.getState().observe(lifecycleOwner, this);
    }

    public void onSaveState(Bundle state) {
        behaviorState = behavior.getState();
        state.putInt(KEY_STATE_BEHAVIOR, behaviorState);
    }

    public void onRestoreState(Bundle state) {
        behaviorState = state.getInt(KEY_STATE_BEHAVIOR);
        nestedScrollView.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                behavior.setState(behaviorState);
                nestedScrollView.getViewTreeObserver().removeOnGlobalLayoutListener(this);
            }
        });
    }

    public void setShowHideBottomBarListener(ShowHideBottomBarListener showHideBottomBarListener) {
        this.showHideBottomBarListener = showHideBottomBarListener;
    }

    public void setVenue(PreviewVenueViewData venueMapViewData) {
        Optional.of(venueDetailsView).ifPresent(VenueDetailsView::clearContent);
        venueViewModel.setVenue(venueMapViewData.getId());
        venueDetailsView.setRetryClickListener(v -> venueViewModel.setVenue(venueMapViewData.getId()));
        openBottomIfNeed();
    }

    private void openBottomIfNeed() {
        int state = behavior.getState();
        if (state == GoogleMapsBottomSheetBehavior.STATE_HIDDEN) {
            behavior.setState(GoogleMapsBottomSheetBehavior.STATE_COLLAPSED);
        }
    }

    private void initBehavior(View view) {
        nestedScrollView = view.findViewById(R.id.nestedScrollView);

        behavior = GoogleMapsBottomSheetBehavior.from(nestedScrollView);
        behavior.setParallax(ivVenuePhoto);

        MergedAppBarLayout mergedAppBarLayout = view.findViewById(R.id.mergedappbarlayout);
        mergedAppBarLayoutBehavior = MergedAppBarLayoutBehavior.from(mergedAppBarLayout);

        mergedAppBarLayoutBehavior.setNavigationOnClickListener(v -> behavior.setState(GoogleMapsBottomSheetBehavior.STATE_HIDDEN));

        behavior.setBottomSheetCallback(new GoogleMapsBottomSheetBehavior.BottomSheetCallback() {
            @Override
            public void onStateChanged(@NonNull View bottomSheet, int newState) {
                if (newState == GoogleMapsBottomSheetBehavior.STATE_HIDDEN) {
                    showHideBottomBarListener.hideBottomBar();
                } else {
                    showHideBottomBarListener.showBottomBar();
                }
            }

            @Override
            public void onSlide(@NonNull View bottomSheet, float slideOffset) {

            }
        });
    }

    public boolean isShown() {
        return behavior.getState() != GoogleMapsBottomSheetBehavior.STATE_HIDDEN;
    }

    public void dismiss() {
        behavior.setState(GoogleMapsBottomSheetBehavior.STATE_HIDDEN);
    }

    @Override
    public void onChanged(@Nullable MessageEvent messageEvent) {
        Optional.of(messageEvent).ifPresent(event ->
                event.handle(() -> toastDelegate.showSuccess(event.getText())));
    }

    private void render(VenueViewState venueViewState) {
        if (venueViewState == null) {
            return;
        }

        if (venueViewState.isLoading()) {
            venueDetailsView.showLoading();
            return;
        } else if (venueViewState.getErrorEvent() != null) {
            ErrorEvent errorEvent = venueViewState.getErrorEvent();
            venueDetailsView.showError(errorEvent.getErrorText());
            return;
        }

        if (venueViewState.getVenueViewData() != null) {
            venueDetailsView.hideLoading();
            showVenue(venueViewState.getVenueViewData());
        }
    }

    private void showVenue(VenueViewData venue) {
        mergedAppBarLayoutBehavior.setToolbarTitle(venue.getTitle());

        String url = Optional.of(venue.getBestPhoto())
                .map(PhotoViewData::getFullSizeUrl)
                .getOrNull();

        ImageLoader.loadImage(ivVenuePhoto, url);

        venueDetailsView.show(venue);
        favoriteButton.setSelected(venue.isFavorite());
        fabCheckInButton.setSelected(venue.isHere());
    }
}
