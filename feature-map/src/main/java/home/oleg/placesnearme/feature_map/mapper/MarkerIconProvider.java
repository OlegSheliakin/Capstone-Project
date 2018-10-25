package home.oleg.placesnearme.feature_map.mapper;

import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;

import com.google.android.gms.maps.model.BitmapDescriptor;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import javax.inject.Inject;

import home.oleg.placenearme.models.Section;
import home.oleg.placesnearme.core_presentation.provider.ResourceProvider;
import home.oleg.placesnearme.feature_map.R;
import home.oleg.placesnearme.feature_map.drawable_converter.DrawableConverter;
import io.reactivex.annotations.NonNull;
import io.reactivex.annotations.Nullable;

/**
 * Created by Oleg Sheliakin on 14.08.2018.
 * Contact me by email - olegsheliakin@gmail.com
 */
public class MarkerIconProvider {

    private final ResourceProvider resourceProvider;
    private final DrawableConverter drawableConverter;
    private final Map<Section, Integer> colors;

    @Inject
    public MarkerIconProvider(ResourceProvider resourceProvider,
                              DrawableConverter drawableConverter,
                              Map<Section, Integer> colors) {
        this.resourceProvider = resourceProvider;
        this.drawableConverter = drawableConverter;
        this.colors = new HashMap<>(colors);
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

    private int getColor(@NonNull Section section) {
        int colorResId = colors.get(section);
        return resourceProvider.getColor(colorResId);
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
