package home.oleg.placesnearme;

import android.content.Context;
import android.widget.SimpleAdapter;

import java.util.List;
import java.util.Map;

/**
 * Created by Oleg on 16.04.2016.
 */
public class DrawerListAdapter extends SimpleAdapter {

    public DrawerListAdapter(Context context, List<? extends Map<String, ?>> data, int resource, String[] from, int[] to) {
        super(context, data, resource, from, to);
    }


}
