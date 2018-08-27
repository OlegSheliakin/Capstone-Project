package home.oleg.placesnearme.presentation.feature.map;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.ShapeDrawable;
import android.support.v4.content.res.ResourcesCompat;

import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;

import javax.inject.Inject;

import home.oleg.placenearme.repositories.Category;
import home.oleg.placesnearme.R;
import home.oleg.placesnearme.utils.BitmapUtils;
import io.reactivex.annotations.NonNull;

public class MarkerIconProvider {

    private final Context context;

    @Inject
    public MarkerIconProvider(Context context) {
        this.context = context;
    }

    public BitmapDescriptor getIconByCategory(@NonNull Category category) {
        Drawable drawable = ResourcesCompat.getDrawable(
                context.getResources(), R.drawable.ic_near_place, null);

        if (drawable == null) {
            return BitmapDescriptorFactory.defaultMarker();
        }

        changeColor(drawable, Color.RED);

        return BitmapUtils.convertToBitmapDescriptor(drawable);
    }

    private void changeColor(Drawable drawable, int color) {
        if (drawable instanceof ShapeDrawable) {
            ShapeDrawable shapeDrawable = (ShapeDrawable) drawable;
            shapeDrawable.getPaint().setColor(color);
        } else if (drawable instanceof GradientDrawable) {
            GradientDrawable gradientDrawable = (GradientDrawable) drawable;
            gradientDrawable.setColor(color);
        } else if (drawable instanceof ColorDrawable) {
            ColorDrawable colorDrawable = (ColorDrawable) drawable;
            colorDrawable.setColor(color);
        }
    }

}
