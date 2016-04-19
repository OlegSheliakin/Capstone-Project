package home.oleg.placesnearme;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.SimpleAdapter;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import home.oleg.placesnearme.mapMVP.IMapPresenter;
import home.oleg.placesnearme.mapMVP.IMapView;

/**
 * Created by Oleg on 19.04.2016.
 */
public class NavigationListAdapter extends SimpleAdapter {

    private final String LATITUDE = "latitude";
    private final String LONGITUDE = "longitude";

    int position;
    Context context;
    IMapView mapView;
    public NavigationListAdapter(Context context, List<? extends Map<String, ?>> data, int resource, String[] from, int[] to, IMapView mapView) {
        super(context, data, resource, from, to);
        this.context = context;
        this.mapView = mapView;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = convertView;
        if (view == null) {
            view = inflater.inflate(R.layout.venue_list_item, parent, false);
        }

        final Button btnGoTo = (Button) view.findViewById(R.id.btnGoTo);
        Button btnCall = (Button) view.findViewById(R.id.btnCall);

        btnGoTo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //mapView.showVenueFromList(position);
                Log.d("TAG", "position = " + position);
            }
        });

        btnCall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO
            }
        });
        return super.getView(position, convertView, parent);
    }
}
