package home.oleg.placesnearme.common.provider;

import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;

import com.google.android.gms.maps.model.BitmapDescriptor;

import java.util.Objects;

import javax.inject.Inject;

import home.oleg.placenearme.repositories.Section;
import home.oleg.placesnearme.R;
import home.oleg.placesnearme.common.converter.DrawableConverter;
import io.reactivex.annotations.Nullable;

/**
 * Created by Oleg Sheliakin on 14.08.2018.
 * Contact me by email - olegsheliakin@gmail.com
 */
public class MarkerIconProvider {

    private final ResourceProvider resourceProvider;
    private final DrawableConverter drawableConverter;

    @Inject
    public MarkerIconProvider(ResourceProvider resourceProvider,
                              DrawableConverter drawableConverter) {
        this.resourceProvider = resourceProvider;
        this.drawableConverter = drawableConverter;
    }

    public BitmapDescriptor getIconByCategory(@Nullable Section section) {
        Drawable drawable = resourceProvider.getDrawable(R.drawable.ic_place_marker);
        Objects.requireNonNull(drawable);

        if (section == null) {
            return drawableConverter.convert(drawable);
        }

        int color = getColor(section);
        changeColor(drawable, color);

        return drawableConverter.convert(drawable);
    }

    private int getColor(Section section) {
        return resourceProvider.getColor(R.color.colorAccent);
    }

    private void changeColor(Drawable drawable, int color) {
        if (drawable instanceof GradientDrawable) {
            GradientDrawable gradientDrawable = (GradientDrawable) drawable;
            gradientDrawable.setColor(color);
        } else {
            throw new IllegalStateException("Drawable should be GradientDrawable");
        }
    }

}
