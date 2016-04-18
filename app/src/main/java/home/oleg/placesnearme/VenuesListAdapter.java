package home.oleg.placesnearme;

import android.content.Context;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import java.util.List;
import java.util.Map;

/**
 * Created by Oleg on 16.04.2016.
 */
public class VenuesListAdapter extends SimpleAdapter {

    public VenuesListAdapter(Context context, List<? extends Map<String, ?>> data, int resource, String[] from, int[] to) {
        super(context, data, resource, from, to);

    }

    @Override
    public void setViewText(TextView v, String text) {
        super.setViewText(v, text);
    }
}
