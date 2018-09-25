package home.oleg.placesnearme.util;

import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

/**
 * Created by Oleg Sheliakin on 21.09.2018.
 * Contact me by email - olegsheliakin@gmail.com
 */
public final class ImageLoader {

    private ImageLoader() {
    }

    public static void loadIcon(ImageView imageView, String url) {
        Glide.with(imageView)
                .load(url)
                .apply(RequestOptions.fitCenterTransform())
                .into(imageView);
    }

    public static void loadImage(ImageView imageView, String url) {
        Glide.with(imageView)
                .load(url)
                .apply(RequestOptions.centerCropTransform())
                .into(imageView);
    }

}
