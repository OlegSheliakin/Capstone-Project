package com.smedialink.feature_venue_detail.venue.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.smedialink.feature_venue_detail.R;

import java.util.ArrayList;
import java.util.List;

import home.oleg.placesnearme.core_presentation.utils.ImageLoader;
import home.oleg.placesnearme.core_presentation.viewdata.PhotoViewData;

public class PhotosAdapter extends RecyclerView.Adapter<PhotosAdapter.ViewHolder> {

    private final List<PhotoViewData> photoViewDatas = new ArrayList<>();

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_photo, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.bind(photoViewDatas.get(position));
    }

    @Override
    public int getItemCount() {
        return photoViewDatas.size();
    }

    @Override
    public void onViewRecycled(@NonNull ViewHolder holder) {
        super.onViewRecycled(holder);
        ImageLoader.clear(holder.imageView);
    }

    public void setItems(List<PhotoViewData> items) {
        photoViewDatas.clear();
        photoViewDatas.addAll(items);
        notifyDataSetChanged();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        private final ImageView imageView;

        private ViewHolder(View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.ivPhoto);
        }

        private void bind(PhotoViewData photoViewData) {
            ImageLoader.loadImage(imageView, photoViewData.getFullSizeUrl());
        }
    }
}
