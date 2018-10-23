package home.oleg.placesnearme.core_presentation.utils;

import android.content.Context;

import home.oleg.placesnearme.core_presentation.R;

/**
 * Created by Oleg Sheliakin on 23.10.2018.
 * Contact me by email - olegsheliakin@gmail.com
 */
public final class DistanceUtil {

    public static final double KILOMETER = 1000.0;

    private DistanceUtil() {
    }

    public static String convertDistanceTOString(double distance, Context context) {
        if (distance >= 1000) {
            double tempKm = distance / KILOMETER;
            return context.getString(R.string.kms, tempKm);
        } else {
            return context.getString(R.string.meters, (int) distance);
        }
    }

}
