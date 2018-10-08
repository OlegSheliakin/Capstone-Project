package home.oleg.placesnearme.core_presentation.utils;

import android.support.annotation.Nullable;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import home.oleg.placesnearme.core_presentation.R;

/**
 * Created by Oleg Sheliakin on 21.09.2018.
 * Contact me by email - olegsheliakin@gmail.com
 */
public final class ImageLoader {

    private ImageLoader() {
    }

    public static void clear(ImageView imageView) {
        Glide.with(imageView).clear(imageView);
    }

    public static void loadIcon(ImageView imageView, @Nullable String url) {
        Glide.with(imageView)
                .load(url)
                .apply(RequestOptions.centerCropTransform())
                .apply(RequestOptions.circleCropTransform())
                .into(imageView);
    }

    public static void loadImage(ImageView imageView, String url) {
        Glide.with(imageView)
                .load(url)
                .apply(RequestOptions.centerCropTransform())
                .into(imageView);
    }

}
