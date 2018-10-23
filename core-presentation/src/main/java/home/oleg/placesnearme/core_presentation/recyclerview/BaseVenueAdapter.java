package home.oleg.placesnearme.core_presentation.recyclerview;

import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.v7.recyclerview.extensions.ListAdapter;
import android.support.v7.util.DiffUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.Collections;
import java.util.List;

import home.oleg.placesnearme.core_presentation.R;

/**
 * Created by Oleg Sheliakin on 23.10.2018.
 * Contact me by email - olegsheliakin@gmail.com
 */
public abstract class BaseVenueAdapter extends ListAdapter<ItemViewType, RecyclerView.ViewHolder> {

    protected BaseVenueAdapter(@NonNull DiffUtil.ItemCallback<ItemViewType> diffCallback) {
        super(diffCallback);
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        RecyclerView.ViewHolder holder;

        switch (viewType) {
            case 1:
                View venueView = LayoutInflater.from(viewGroup.getContext())
                        .inflate(getVenueItemLayoutRes(), viewGroup, false);
                holder = getVenueItemHolder(venueView);
                break;
            default:
                View emptyView = LayoutInflater.from(viewGroup.getContext())
                        .inflate(getEmptyItemLayoutRes(), viewGroup, false);
                holder = new BaseVenueAdapter.EmptyViewHolder(emptyView);
                break;
        }

        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
        ItemViewType itemViewType = getItem(i);

        if (itemViewType instanceof VenueViewItem && viewHolder instanceof BaseVenueAdapter.VenueItemHolder) {
            ((BaseVenueAdapter.VenueItemHolder) viewHolder).bind((VenueViewItem) itemViewType);
        }
    }

    @Override
    public int getItemViewType(int position) {
        return getItem(position).getViewType();
    }

    public void showEmpty() {
        List<ItemViewType> newList = Collections.singletonList(new EmptyViewItem());
        submitList(newList);
    }

    public abstract static class VenueItemHolder extends RecyclerView.ViewHolder {
        public VenueItemHolder(@NonNull View itemView) {
            super(itemView);
        }

        protected abstract void bind(VenueViewItem venueViewItem);
    }

    private static final class EmptyViewHolder extends RecyclerView.ViewHolder {

        private EmptyViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }

    protected abstract BaseVenueAdapter.VenueItemHolder getVenueItemHolder(View view);

    @LayoutRes
    protected abstract int getVenueItemLayoutRes();

    @LayoutRes
    protected abstract int getEmptyItemLayoutRes();

}
