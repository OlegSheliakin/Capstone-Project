package home.oleg.placesnearme.common.provider;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;

import javax.inject.Inject;

/**
 * Created by Oleg Sheliakin on 28.08.2018.
 * Contact me by email - olegsheliakin@gmail.com
 */
public class ResourceProviderImpl implements ResourceProvider {

    private final Context context;

    @Inject
    public ResourceProviderImpl(Context context) {
        this.context = context;
    }

    @Override
    public Drawable getDrawable(int resId) {
        return ContextCompat.getDrawable(context, resId);
    }

    @Override
    public String getString(int resId) {
        return context.getString(resId);
    }

    @Override
    public int getColor(int resId) {
        return ContextCompat.getColor(context, resId);
    }
}
