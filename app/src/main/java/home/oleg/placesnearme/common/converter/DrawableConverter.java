package home.oleg.placesnearme.common.converter;

import android.graphics.drawable.Drawable;
import android.support.annotation.DrawableRes;

import com.google.android.gms.maps.model.BitmapDescriptor;

import io.reactivex.annotations.NonNull;

/**
 * Created by Oleg Sheliakin on 28.08.2018.
 * Contact me by email - olegsheliakin@gmail.com
 */
public interface DrawableConverter {

    BitmapDescriptor convert(@NonNull Drawable drawable);

}
