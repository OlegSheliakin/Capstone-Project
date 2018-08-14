package home.oleg.placesnearme.presentation;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;
import java.util.Map;

import home.oleg.placesnearme.R;
import home.oleg.placesnearme.presentation.feature.map.view.IMapView;

/**
 * Created by Oleg on 30.05.2016.
 */
public class VenueRecyclerViewAdapter extends RecyclerView.Adapter<VenueRecyclerViewAdapter.ViewHolder> {

    private Context context;
    private IMapView mapView;
    private List<Map<String, String>> data;

    public VenueRecyclerViewAdapter(Context context, IMapView mapView, List<Map<String, String>> data) {
        this.context = context;
        this.mapView = mapView;
        this.data = data;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).
                inflate(R.layout.venue_list_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        holder.btnGoTo.setOnClickListener(v -> mapView.showVenueFromList(position));

        holder.btnCall.setOnClickListener(v -> mapView.callIntent(position));
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView imageViewVenuePhoto;
        private TextView tvName;
        private TextView tvAddress;
        private TextView tvPhone;
        private TextView tvDistance;
        private Button btnGoTo;
        private Button btnCall;

        public ViewHolder(View view) {
            super(view);
            imageViewVenuePhoto = view.findViewById(R.id.imageViewVenuePhoto);
            tvName = view.findViewById(R.id.tvName);
            tvAddress = view.findViewById(R.id.tvAddress);
            tvPhone = view.findViewById(R.id.tvPhone);
            tvDistance = view.findViewById(R.id.tvDistance);
            btnGoTo = view.findViewById(R.id.btnGoTo);
            btnCall = view.findViewById(R.id.btnCall);
        }
    }
}

