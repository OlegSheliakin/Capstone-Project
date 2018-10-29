package home.oleg.placesnearme.core_presentation.recyclerview;

import android.support.v7.util.DiffUtil;

import io.reactivex.annotations.NonNull;

public final class VenuesDiffCallback extends DiffUtil.ItemCallback<ItemViewType> {

    public static VenuesDiffCallback VENUES_DIFF_CALLBACK = new VenuesDiffCallback();

    private VenuesDiffCallback() {

    }

    @Override
    public boolean areItemsTheSame(@NonNull ItemViewType itemViewType, @NonNull ItemViewType t1) {
        return itemViewType.equals(t1);
    }

    @Override
    public boolean areContentsTheSame(@NonNull ItemViewType itemViewType, @NonNull ItemViewType t1) {
        return itemViewType.equals(t1);
    }

}
