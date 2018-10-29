package home.oleg.feature_favorite_venues;

import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.util.DiffUtil;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import home.oleg.placesnearme.core_presentation.recyclerview.BaseVenueAdapter;
import home.oleg.placesnearme.core_presentation.recyclerview.ItemViewType;
import home.oleg.placesnearme.core_presentation.recyclerview.VenueViewItem;
import home.oleg.placesnearme.core_presentation.utils.ImageLoader;
import home.oleg.placesnearme.core_presentation.viewdata.VenueViewData;

public final class FavoriteVenuesAdapter extends BaseVenueAdapter {

    private final FavoriteVenuesAdapter.FavoriteClicksListener favoriteClicksListener;

    public FavoriteVenuesAdapter(@NonNull DiffUtil.ItemCallback<ItemViewType> diffCallback,
                                 @NonNull FavoriteVenuesAdapter.FavoriteClicksListener favoriteClicksListener) {
        super(diffCallback);
        this.favoriteClicksListener = favoriteClicksListener;
    }

    public interface FavoriteClicksListener {
        void favoriteClicked(VenueViewData venueViewData);

        void onItemClicked(VenueViewData venueViewData);
    }

    @Override
    protected BaseVenueAdapter.VenueItemHolder getVenueItemHolder(View view) {
        return new FavoriteVenuesAdapter.ViewHolder(view, favoriteClicksListener);
    }

    @Override
    protected int getVenueItemLayoutRes() {
        return R.layout.item_favorite_venue;
    }

    @Override
    protected int getEmptyItemLayoutRes() {
        return R.layout.item_empty;
    }

    private static final class ViewHolder extends BaseVenueAdapter.VenueItemHolder {

        private ImageView ivPhoto;
        private TextView tvName;
        private TextView tvAddress;
        private FloatingActionButton fabAddToFavorite;

        private FavoriteVenuesAdapter.FavoriteClicksListener favoriteClicksListener;

        private ViewHolder(@NonNull View itemView,
                           @NonNull FavoriteVenuesAdapter.FavoriteClicksListener favoriteClicksListener) {
            super(itemView);
            this.favoriteClicksListener = favoriteClicksListener;

            this.ivPhoto = itemView.findViewById(R.id.ivPhoto);
            this.tvName = itemView.findViewById(R.id.tvName);
            this.tvAddress = itemView.findViewById(R.id.tvAddress);
            this.fabAddToFavorite = itemView.findViewById(R.id.fabAddToFavorite);
        }

        @Override
        protected void bind(VenueViewItem venueViewItem) {
            VenueViewData venueViewData = venueViewItem.getVenueViewData();
            tvName.setText(venueViewData.getTitle());
            fabAddToFavorite.setSelected(venueViewData.isFavorite());
            fabAddToFavorite.setOnClickListener(v -> {
                favoriteClicksListener.favoriteClicked(venueViewData);
            });

            itemView.setOnClickListener(v -> favoriteClicksListener.onItemClicked(venueViewData));

            if (venueViewData.getBestPhoto() != null) {
                ImageLoader.loadImage(ivPhoto, venueViewData.getBestPhoto().getFullSizeUrl());
            } else {
                ImageLoader.loadImage(ivPhoto, null);
            }

            tvAddress.setText(venueViewData.getAddress());
        }
    }

}
