package home.oleg.placesnearme.core_presentation.mapper;

import home.oleg.placenearme.models.Icon;
import home.oleg.placesnearme.core_presentation.viewdata.IconViewData;

public final class IconViewMapper {

    public static IconViewData map(Icon icon) {
        IconViewData iconViewData = new IconViewData();
        iconViewData.setPrefix(icon.getPrefix());
        iconViewData.setSuffix(icon.getSuffix());
        return iconViewData;
    }

}
