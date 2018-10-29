package home.oleg.placesnearme.core_presentation.utils;

import android.graphics.Bitmap;
import android.support.annotation.Nullable;
import android.widget.ImageView;
import android.widget.RemoteViews;

import com.squareup.picasso.Picasso;

import java.io.IOException;

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
        Picasso.get().cancelRequest(imageView);
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

    public static Bitmap loadIcon(RemoteViews remoteViews, int id, @Nullable String url, int appwidgetId) throws IOException {
        return Picasso.get()
                .load(url)
                .transform(new CropCircleTransformation())
                .get();
    }

}
