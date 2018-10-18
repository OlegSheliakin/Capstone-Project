package home.oleg.placesnearme.core_presentation.mapper;

import home.oleg.placesnearme.core_presentation.viewdata.IconViewData;

public final class IconViewMapper {

    public static IconViewData map(String prefix, String suffix) {
        IconViewData iconViewData = new IconViewData();
        iconViewData.setPrefix(prefix);
        iconViewData.setSuffix(suffix);
        return iconViewData;
    }

}
