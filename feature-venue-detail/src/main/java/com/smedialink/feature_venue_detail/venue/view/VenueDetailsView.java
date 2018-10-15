package com.smedialink.feature_venue_detail.venue.view;

import android.content.Context;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.ViewTreeObserver;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.smedialink.common.Optional;
import com.smedialink.feature_venue_detail.R;

import java.util.Collections;

import home.oleg.coordinator_behavior.GoogleMapsBottomSheetBehavior;
import home.oleg.placesnearme.core_presentation.utils.ImageLoader;
import home.oleg.placesnearme.core_presentation.viewdata.IconViewData;
import home.oleg.placesnearme.core_presentation.viewdata.PhotoViewData;
import home.oleg.placesnearme.core_presentation.viewdata.ShortVenueViewData;
import home.oleg.placesnearme.core_presentation.viewdata.VenueViewData;
import io.rmiri.skeleton.SkeletonGroup;

/**
 * Created by Oleg Sheliakin on 20.09.2018.
 * Contact me by email - olegsheliakin@gmail.com
 */
public class VenueDetailsView extends SkeletonGroup {

    private TextView tvVenueName;
    private TextView tvVenueAddress;
    private TextView tvVenueDescription;
    private TextView tvVenueDistance;
    private TextView tvVenueOpeningHours;
    private TextView tvCategoryName;
    private TextView tvContacts;
    private RatingBar rating;
    private ImageView ivVenueIcon;

    private PhotosAdapter photosAdapter = new PhotosAdapter();

    public VenueDetailsView(Context context) {
        this(context, null);
    }

    public VenueDetailsView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public VenueDetailsView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    public void show(VenueViewData venue) {
        tvVenueName.setText(venue.getTitle());
        tvVenueAddress.setText(venue.getAddress());
        tvVenueDescription.setText(venue.getDescription());
        tvVenueOpeningHours.setText(venue.getOpeningHoursStatus());
        tvContacts.setText(venue.getFormattedPhone());
        rating.setRating(venue.getRating());

        photosAdapter.setItems(venue.getPhotos());
    }

    public void showShortVenue(ShortVenueViewData venue) {
        tvVenueName.setText(venue.getTitle());
        tvVenueAddress.setText(venue.getAddress());

        Optional.of(venue.getDistance()).ifPresent(aDouble -> {
            String distance = getContext().getString(R.string.meters, aDouble.intValue());
            tvVenueDistance.setText(distance);
        });

        tvCategoryName.setText(venue.getCategoryName());

        String url = Optional.of(venue.getIconViewData())
                .map(IconViewData::getIconUrlGray)
                .getOrNull();

        ImageLoader.loadIcon(ivVenueIcon, url);
    }

    public void clearContent() {
        tvVenueName.setText("");
        tvVenueAddress.setText("");
        tvVenueDescription.setText("");
        tvContacts.setText("");
        tvVenueOpeningHours.setText("");
        rating.setRating(0f);
        photosAdapter.setItems(Collections.emptyList());
    }

    private void init() {
        LayoutInflater.from(getContext())
                .inflate(R.layout.merge_view_venue_details, this, true);

        setAutoPlay(false);

        tvVenueName = findViewById(R.id.tvVenueName);
        tvVenueAddress = findViewById(R.id.tvVenueAddress);
        tvVenueDescription = findViewById(R.id.tvVenueDescription);
        tvVenueDistance = findViewById(R.id.tvVenueDistance);
        tvVenueOpeningHours = findViewById(R.id.tvVenueOpeningHours);
        tvContacts = findViewById(R.id.tvContacts);
        ivVenueIcon = findViewById(R.id.ivVenueIcon);
        tvCategoryName = findViewById(R.id.tvCategoryName);
        rating = findViewById(R.id.ratingBar);

        RecyclerView rvPhotos = findViewById(R.id.rvPhotos);
        rvPhotos.setLayoutManager(new LinearLayoutManager(this.getContext()));
        rvPhotos.setAdapter(photosAdapter);
    }

}
