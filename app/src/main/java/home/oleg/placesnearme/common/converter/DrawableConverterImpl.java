package home.oleg.placesnearme.common.converter;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;

import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;

import javax.inject.Inject;

/**
 * Created by Oleg Sheliakin on 28.08.2018.
 * Contact me by email - olegsheliakin@gmail.com
 */
public class DrawableConverterImpl implements DrawableConverter {

    @Inject
    public DrawableConverterImpl() {}

    @Override
    public BitmapDescriptor convert(Drawable drawable) {
        Bitmap bitmap = Bitmap.createBitmap(drawable.getIntrinsicWidth(),
                drawable.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        drawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
        drawable.draw(canvas);
        return BitmapDescriptorFactory.fromBitmap(bitmap);
    }
}
