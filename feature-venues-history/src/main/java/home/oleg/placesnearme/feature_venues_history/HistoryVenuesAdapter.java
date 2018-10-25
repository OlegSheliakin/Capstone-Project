package home.oleg.placesnearme.feature_venues_history;

import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.util.DiffUtil;
import android.view.View;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.smedialink.common.Optional;

import home.oleg.placesnearme.core_presentation.recyclerview.BaseVenueAdapter;
import home.oleg.placesnearme.core_presentation.recyclerview.ItemViewType;
import home.oleg.placesnearme.core_presentation.recyclerview.VenueViewItem;
import home.oleg.placesnearme.core_presentation.utils.DistanceUtil;
import home.oleg.placesnearme.core_presentation.utils.ImageLoader;
import home.oleg.placesnearme.core_presentation.viewdata.VenueViewData;

public final class HistoryVenuesAdapter extends BaseVenueAdapter {

    private final HistoryVenuesAdapter.HistoryClicksListener favoriteClicksListener;

    public HistoryVenuesAdapter(
            @NonNull DiffUtil.ItemCallback<ItemViewType> diffCallback,
            HistoryVenuesAdapter.HistoryClicksListener favoriteClicksListener) {
        super(diffCallback);
        this.favoriteClicksListener = favoriteClicksListener;
    }

    public interface HistoryClicksListener {
        void favoriteClicked(VenueViewData venueViewData);

        void onItemClicked(VenueViewData venueViewData);
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

        private HistoryVenuesAdapter.HistoryClicksListener historyClicksListener;

        private ViewHolder(@NonNull View itemView, HistoryVenuesAdapter.HistoryClicksListener historyClicksListener) {
            super(itemView);
            this.historyClicksListener = historyClicksListener;

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
            ratingBar.setRating(venueViewData.getAdoptedRating());

            fabAddToFavorite.setOnClickListener(v -> historyClicksListener.favoriteClicked(venueViewData));
            itemView.setOnClickListener(v -> historyClicksListener.onItemClicked(venueViewData));

            if (venueViewData.getBestPhoto() != null) {
                ImageLoader.loadIcon(ivVenueIcon, venueViewData.getCategoryIconUrl());
            } else {
                ImageLoader.loadIcon(ivVenueIcon, null);
            }

            tvAddress.setText(venueViewData.getAddress());
        }
    }

}
