package home.oleg.placesnearme.feature_venues_history;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.recyclerview.extensions.ListAdapter;
import android.support.v7.util.DiffUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.smedialink.common.Optional;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import home.oleg.placesnearme.core_presentation.recyclerview.BaseVenueAdapter;
import home.oleg.placesnearme.core_presentation.recyclerview.EmptyViewItem;
import home.oleg.placesnearme.core_presentation.recyclerview.ItemViewType;
import home.oleg.placesnearme.core_presentation.recyclerview.VenueViewItem;
import home.oleg.placesnearme.core_presentation.recyclerview.VenuesDiffCallback;
import home.oleg.placesnearme.core_presentation.utils.DistanceUtil;
import home.oleg.placesnearme.core_presentation.utils.ImageLoader;
import home.oleg.placesnearme.core_presentation.viewdata.VenueViewData;

public final class HistoryVenuesAdapter extends BaseVenueAdapter {

    private final HistoryVenuesAdapter.FavoriteClicksListener favoriteClicksListener;

    public HistoryVenuesAdapter(
            @NonNull DiffUtil.ItemCallback<ItemViewType> diffCallback,
            HistoryVenuesAdapter.FavoriteClicksListener favoriteClicksListener) {
        super(diffCallback);
        this.favoriteClicksListener = favoriteClicksListener;
    }

    public interface FavoriteClicksListener {
        void favoriteClicked(VenueViewData venueViewData);

        void showOnMapClicked(VenueViewData venueViewData);
    }

    @Override
    protected BaseVenueAdapter.VenueItemHolder getVenueItemHolder(View view) {
        return new HistoryVenuesAdapter.ViewHolder(view, favoriteClicksListener);
    }

    @Override
    protected int getVenueItemLayoutRes() {
        return R.layout.item_history_venue;
    }

    @Override
    protected int getEmptyItemLayoutRes() {
        return R.layout.item_empty;
    }

    private static final class ViewHolder extends BaseVenueAdapter.VenueItemHolder {

        private ImageView ivVenueIcon;
        private TextView tvVenueName;
        private TextView tvAddress;
        private TextView tvVenueDistance;
        private TextView tvCategoryName;
        private RatingBar ratingBar;
        private FloatingActionButton fabAddToFavorite;

        private HistoryVenuesAdapter.FavoriteClicksListener favoriteClicksListener;

        private ViewHolder(@NonNull View itemView, HistoryVenuesAdapter.FavoriteClicksListener favoriteClicksListener) {
            super(itemView);
            this.favoriteClicksListener = favoriteClicksListener;

            this.ivVenueIcon = itemView.findViewById(R.id.ivVenueIcon);
            this.tvVenueName = itemView.findViewById(R.id.tvVenueName);
            this.tvAddress = itemView.findViewById(R.id.tvVenueAddress);
            this.tvVenueDistance = itemView.findViewById(R.id.tvVenueDistance);
            this.tvCategoryName = itemView.findViewById(R.id.tvCategoryName);
            this.ratingBar = itemView.findViewById(R.id.ratingBar);
            this.fabAddToFavorite = itemView.findViewById(R.id.fabAddToFavorite);
        }

        @Override
        protected void bind(VenueViewItem venueViewItem) {
            VenueViewData venueViewData = venueViewItem.getVenueViewData();
            tvVenueName.setText(venueViewData.getTitle());
            fabAddToFavorite.setSelected(venueViewData.isFavorite());
            Optional.of(venueViewData.getDistance()).ifPresent(aDouble -> {
                String distance = DistanceUtil.convertDistanceTOString(aDouble, itemView.getContext());
                tvVenueDistance.setText(distance);
            });

            tvCategoryName.setText(venueViewData.getCategoryName());
            ratingBar.setRating(venueViewData.getRating());

            fabAddToFavorite.setOnClickListener(v -> {
                favoriteClicksListener.favoriteClicked(venueViewData);
            });

            if (venueViewData.getBestPhoto() != null) {
                ImageLoader.loadIcon(ivVenueIcon, venueViewData.getCategoryIconUrl());
            } else {
                ImageLoader.loadIcon(ivVenueIcon, null);
            }

            tvAddress.setText(venueViewData.getAddress());
        }
    }

}
