package home.oleg.placesnearme;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;
import java.util.Map;

import home.oleg.placesnearme.map_mvp.IMapView;

import static home.oleg.placesnearme.map_mvp.impl.MapViewImpl.*;

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

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = (View) LayoutInflater.from(parent.getContext()).
                inflate(R.layout.venue_list_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        String name = data.get(position).get(ATTRIBUTE_VENUE_NAME);
        String distance = data.get(position).get(ATTRIBUTE_VENUE_DISTANCE);
        String address = data.get(position).get(ATTRIBUTE_VENUE_ADDRESS);
        String phone = data.get(position).get(ATTRIBUTE_VENUE_PHONE);
        holder.tvName.setText(name);
        holder.tvDistance.setText(distance);
        holder.tvAddress.setText(address);
        holder.tvPhone.setText(phone);

        String URL = data.get(position).get(ATTRIBUTE_VENUE_PHOTO);
        if (URL != null) {
            Picasso.with(context)
                    .load(URL)
                    .into(holder.imageViewVenuePhoto);
        }

        if (phone == null) {
            holder.btnCall.setEnabled(false);
        } else {
            holder.btnCall.setEnabled(true);
        }

        holder.btnGoTo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mapView.showVenueFromList(position);
            }
        });

        holder.btnCall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mapView.callIntent(position);
            }
        });
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
            imageViewVenuePhoto = (ImageView) view.findViewById(R.id.imageViewVenuePhoto);
            tvName = (TextView) view.findViewById(R.id.tvName);
            tvAddress = (TextView) view.findViewById(R.id.tvAddress);
            tvPhone = (TextView) view.findViewById(R.id.tvPhone);
            tvDistance = (TextView) view.findViewById(R.id.tvDistance);
            btnGoTo = (Button) view.findViewById(R.id.btnGoTo);
            btnCall = (Button) view.findViewById(R.id.btnCall);
        }
    }
}

