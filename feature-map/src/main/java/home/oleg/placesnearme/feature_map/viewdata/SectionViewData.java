package home.oleg.placesnearme.feature_map.viewdata;

import android.support.annotation.DrawableRes;
import android.support.annotation.StringRes;

import home.oleg.placenearme.models.Section;

/**
 * Created by Oleg Sheliakin on 25.10.2018.
 * Contact me by email - olegsheliakin@gmail.com
 */
public class SectionViewData {

    private final Section section;
    @StringRes
    private final int sectionNameRes;
    @DrawableRes
    private final int sectionDrawableRes;

    public SectionViewData(Section section, int sectionNameRes, int sectionDrawableRes) {
        this.section = section;
        this.sectionNameRes = sectionNameRes;
        this.sectionDrawableRes = sectionDrawableRes;
    }

    public Section getSection() {
        return section;
    }

    public int getSectionNameRes() {
        return sectionNameRes;
    }

    public int getSectionDrawableRes() {
        return sectionDrawableRes;
    }

}
