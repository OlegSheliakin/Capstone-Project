package home.oleg.feature_favorite_venues;

import android.support.v7.util.DiffUtil;

import java.util.List;

public class FavoriteVenusDiffCallback extends DiffUtil.Callback {

    private final List<ItemViewType> oldList;
    private final List<ItemViewType> newList;

    public FavoriteVenusDiffCallback(List<ItemViewType> oldList, List<ItemViewType> newList) {
        this.oldList = oldList;
        this.newList = newList;
    }

    @Override
    public int getOldListSize() {
        return oldList.size();
    }

    @Override
    public int getNewListSize() {
        return newList.size();
    }

    @Override
    public boolean areItemsTheSame(int oldItemPosition, int newItemPosition) {
        return oldList.get(oldItemPosition).getViewType() == newList.get(newItemPosition).getViewType();
    }

    @Override
    public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
        return oldList.get(oldItemPosition).equals(newList.get(newItemPosition));
    }
}
