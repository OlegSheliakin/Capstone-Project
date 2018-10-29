package home.oleg.placesnearme.feature_map.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import home.oleg.placenearme.models.Section;
import home.oleg.placesnearme.feature_map.R;
import home.oleg.placesnearme.feature_map.viewdata.SectionViewData;

/**
 * Created by Oleg Sheliakin on 25.10.2018.
 * Contact me by email - olegsheliakin@gmail.com
 */
public class SectionsAdapter extends RecyclerView.Adapter<SectionsAdapter.ViewHolder> implements ItemSelectListener {

    private final List<CheckedItem<SectionViewData>> checkedItems;
    private final SectionSelectListener sectionSelectListener;

    public SectionsAdapter(List<CheckedItem<SectionViewData>> checkedItems, SectionSelectListener sectionSelectListener) {
        this.checkedItems = checkedItems;
        this.sectionSelectListener = sectionSelectListener;
    }

    public int getSelectedItemPosition() {
        int selectedItemPosition = -1;
        for (int i = 0; i < checkedItems.size(); i++) {
            if (checkedItems.get(i).isChecked()) {
                selectedItemPosition = i;
            }
        }
        return selectedItemPosition;
    }

    public Section getSelectedItem() {
        int selectedItemPosition = getSelectedItemPosition();
        if (selectedItemPosition != -1) {
            return checkedItems.get(selectedItemPosition).getData().getSection();
        }
        return null;
    }

    public interface SectionSelectListener {
        void sectionSelected(Section section);
    }

    @NonNull
    @Override
    public SectionsAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_section, viewGroup, false);
        return new SectionsAdapter.ViewHolder(view, this);
    }

    @Override
    public void onBindViewHolder(@NonNull SectionsAdapter.ViewHolder viewHolder, int i) {
        viewHolder.bind(checkedItems.get(i));
    }

    @Override
    public int getItemCount() {
        return checkedItems.size();
    }

    @Override
    public void itemSelected(int position) {
        setSelected(position);
        sectionSelectListener.sectionSelected(checkedItems.get(position).getData().getSection());
    }

    public void setSelected(int position) {
        if (position == -1) {
            return;
        }

        for (CheckedItem<SectionViewData> checkedItem : checkedItems) {
            checkedItem.setChecked(false);
        }
        checkedItems.get(position).setChecked(true);
        notifyDataSetChanged();
    }

    static final class ViewHolder extends RecyclerView.ViewHolder {

        private final TextView textView;

        private ViewHolder(@NonNull View itemView, ItemSelectListener itemSelectListener) {
            super(itemView);
            textView = itemView.findViewById(R.id.tvSection);
            textView.setOnClickListener(view -> itemSelectListener.itemSelected(getAdapterPosition()));
        }

        private void bind(CheckedItem<SectionViewData> checkedItem) {
            SectionViewData sectionViewData = checkedItem.getData();
            textView.setSelected(checkedItem.isChecked());
            textView.setText(sectionViewData.getSectionNameRes());
            textView.setCompoundDrawablesWithIntrinsicBounds(0, sectionViewData.getSectionDrawableRes(), 0, 0);
        }
    }
}
