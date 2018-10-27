package com.smedialink.feature_venue_detail.venue.view;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RatingBar;
import android.widget.TextView;

import com.smedialink.common.Optional;
import com.smedialink.feature_venue_detail.R;

import java.util.Collections;

import home.oleg.placesnearme.core_presentation.utils.ImageLoader;
import home.oleg.placesnearme.core_presentation.viewdata.IconViewData;
import home.oleg.placesnearme.core_presentation.viewdata.VenueViewData;

/**
 * Created by Oleg Sheliakin on 20.09.2018.
 * Contact me by email - olegsheliakin@gmail.com
 */
public class VenueDetailsView extends FrameLayout {

    private TextView tvVenueName;
    private TextView tvVenueAddress;
    private TextView tvVenueDescription;
    private TextView tvVenueDistance;
    private TextView tvVenueOpeningHours;
    private TextView tvCategoryName;
    private TextView tvContacts;
    private RatingBar rating;
    private ViewGroup content;
    private ImageView ivVenueIcon;
    private ProgressBar progressBar;
    private Button retryButton;
    private TextView tvError;
    private RetryClickListener retryClickListener;

    public interface RetryClickListener {
        void onRetryClick();
    }

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

    public void setRetryClickListener(@NonNull RetryClickListener retryClickListener) {
        this.retryClickListener = retryClickListener;

        retryButton.setOnClickListener(v -> retryClickListener.onRetryClick());
    }

    public void show(VenueViewData venue) {
        tvError.setVisibility(GONE);
        retryButton.setVisibility(GONE);
        progressBar.setVisibility(GONE);

        tvVenueDescription.setText(venue.getDescription());
        tvVenueOpeningHours.setText(venue.getOpeningHoursStatus());
        tvContacts.setText(venue.getFormattedPhone());
        rating.setRating(venue.getAdoptedRating());

        photosAdapter.setItems(venue.getPhotos());

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

        content.setVisibility(VISIBLE);
    }

    public void clearContent() {
        tvVenueName.setText("");
        tvVenueAddress.setText("");
        tvVenueDescription.setText("");
        tvContacts.setText("");
        tvVenueOpeningHours.setText("");
        rating.setRating(0f);
        photosAdapter.setItems(Collections.emptyList());
        tvError.setText("");
    }

    public void showLoading() {
        content.setVisibility(GONE);
        tvError.setVisibility(GONE);
        retryButton.setVisibility(GONE);
        progressBar.setVisibility(VISIBLE);
    }

    public void hideLoading() {
        content.setVisibility(VISIBLE);
        tvError.setVisibility(GONE);
        retryButton.setVisibility(GONE);
        progressBar.setVisibility(GONE);
    }

    public void showError(String text) {
        content.setVisibility(GONE);
        retryButton.setVisibility(VISIBLE);
        tvError.setVisibility(VISIBLE);
        tvError.setText(text);
        progressBar.setVisibility(GONE);
    }

    private void init() {
        LayoutInflater.from(getContext())
                .inflate(R.layout.view_venue_details, this, true);

        content = findViewById(R.id.content);
        content.setVisibility(INVISIBLE);

        tvVenueName = findViewById(R.id.tvVenueName);
        tvVenueAddress = findViewById(R.id.tvVenueAddress);
        tvVenueDescription = findViewById(R.id.tvVenueDescription);
        tvVenueDistance = findViewById(R.id.tvVenueDistance);
        tvVenueOpeningHours = findViewById(R.id.tvVenueOpeningHours);
        tvContacts = findViewById(R.id.tvContacts);
        ivVenueIcon = findViewById(R.id.ivVenueIcon);
        tvCategoryName = findViewById(R.id.tvCategoryName);
        rating = findViewById(R.id.ratingBar);
        progressBar = findViewById(R.id.progressBar);
        tvError = findViewById(R.id.tvError);
        retryButton = findViewById(R.id.retryButton);

        RecyclerView rvPhotos = findViewById(R.id.rvPhotos);
        rvPhotos.setLayoutManager(new LinearLayoutManager(this.getContext()));
        rvPhotos.setAdapter(photosAdapter);
    }

}
