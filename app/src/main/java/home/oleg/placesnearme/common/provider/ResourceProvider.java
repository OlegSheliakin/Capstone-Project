package home.oleg.placesnearme.common.provider;

import android.graphics.drawable.Drawable;
import android.support.annotation.ColorRes;
import android.support.annotation.DrawableRes;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;

/**
 * Created by Oleg Sheliakin on 28.08.2018.
 * Contact me by email - olegsheliakin@gmail.com
 */
public interface ResourceProvider {

    @Nullable
    Drawable getDrawable(@DrawableRes int resId);

    @Nullable
    String getString(@StringRes int resId);

    int getColor(@ColorRes int resId);

}
