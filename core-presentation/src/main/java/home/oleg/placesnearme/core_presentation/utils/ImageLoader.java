package home.oleg.placesnearme.core_presentation.utils;

import android.support.annotation.Nullable;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.squareup.picasso.Picasso;

import home.oleg.placesnearme.core_presentation.R;
import jp.wasabeef.picasso.transformations.CropCircleTransformation;

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
        Picasso.get()
                .load(url)
                .transform(new CropCircleTransformation())
                .placeholder(R.drawable.placeholder_icon)
                .into(imageView);
    }

    public static void loadImage(ImageView imageView, @Nullable String url) {
        Picasso.get()
                .load(url)
                .fit()
                .centerCrop()
                .placeholder(R.color.colorGrayLight)
                .error(R.color.colorGrayLight)
                .into(imageView);
    }

}
