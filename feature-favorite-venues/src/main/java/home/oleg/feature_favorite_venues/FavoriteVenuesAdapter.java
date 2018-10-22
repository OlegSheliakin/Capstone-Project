package home.oleg.feature_favorite_venues;

import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.util.DiffUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.inject.Inject;

import home.oleg.placesnearme.core_presentation.utils.ImageLoader;
import home.oleg.placesnearme.core_presentation.viewdata.VenueViewData;

public final class FavoriteVenuesAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private final List<ItemViewType> data = new ArrayList<>();
    private final FavoriteClicksListener favoriteClicksListener;

    public interface FavoriteClicksListener {
        void favoriteClicked(VenueViewData venueViewData);

        void showOnMapClicked(VenueViewData venueViewData);
    }

    @Inject
    public FavoriteVenuesAdapter(FavoriteClicksListener favoriteClicksListener) {
        this.favoriteClicksListener = favoriteClicksListener;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        RecyclerView.ViewHolder holder;

        switch (viewType) {
            case 1:
                View venueView = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_favorite_venue, viewGroup, false);
                holder = new ViewHolder(venueView, favoriteClicksListener);
                break;
            default:
                View emptyView = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_empty, viewGroup, false);
                holder = new EmptyViewHolder(emptyView);
                break;
        }

        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
        ItemViewType itemViewType = data.get(i);

        if (itemViewType instanceof VenueViewItem && viewHolder instanceof ViewHolder) {
            ((ViewHolder) viewHolder).bind((VenueViewItem) itemViewType);
        }
    }

    @Override
    public int getItemViewType(int position) {
        return data.get(position).getViewType();
    }

    public void showEmpty() {
        List<ItemViewType> newList = Collections.singletonList(new EmptyViewItem());
        DiffUtil.DiffResult diffResult = DiffUtil.calculateDiff(
                new FavoriteVenusDiffCallback(this.data, newList)
        );

        diffResult.dispatchUpdatesTo(this);

        data.clear();
        data.addAll(newList);

    }

    public void setItems(List<VenueViewItem> detailedVenues) {
        List<ItemViewType> newList = new ArrayList<>(detailedVenues);
        DiffUtil.DiffResult diffResult = DiffUtil.calculateDiff(
                new FavoriteVenusDiffCallback(data, newList));
        diffResult.dispatchUpdatesTo(this);

        data.clear();
        data.addAll(newList);
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    private static class ViewHolder extends RecyclerView.ViewHolder {

        private ImageView ivPhoto;
        private TextView tvName;
        private TextView tvAddress;
        private FloatingActionButton fabAddToFavorite;
        private FloatingActionButton fabShowOnMap;

        private FavoriteClicksListener favoriteClicksListener;

        private ViewHolder(@NonNull View itemView, FavoriteClicksListener favoriteClicksListener) {
            super(itemView);
            this.favoriteClicksListener = favoriteClicksListener;

            this.ivPhoto = itemView.findViewById(R.id.ivPhoto);
            this.tvName = itemView.findViewById(R.id.tvName);
            this.tvAddress = itemView.findViewById(R.id.tvAddress);
            this.fabAddToFavorite = itemView.findViewById(R.id.fabAddToFavorite);
            this.fabShowOnMap = itemView.findViewById(R.id.fabShowOnMap);
        }

        private void bind(VenueViewItem venueViewItem) {
            VenueViewData venueViewData = venueViewItem.getVenueViewData();

            tvName.setText(venueViewData.getTitle());
            fabAddToFavorite.setSelected(venueViewData.isFavorite());
            fabAddToFavorite.setOnClickListener(v -> {
                favoriteClicksListener.favoriteClicked(venueViewData);
            });

            fabShowOnMap.setOnClickListener(v -> {
                favoriteClicksListener.showOnMapClicked(venueViewData);
            });

            if (venueViewData.getBestPhoto() != null) {
                ImageLoader.loadImage(ivPhoto, venueViewData.getBestPhoto().getFullSizeUrl());
            } else {
                ImageLoader.loadImage(ivPhoto, null);
            }

            tvAddress.setText(venueViewData.getAddress());
        }
    }

    private static class EmptyViewHolder extends RecyclerView.ViewHolder {

        private EmptyViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }
}
