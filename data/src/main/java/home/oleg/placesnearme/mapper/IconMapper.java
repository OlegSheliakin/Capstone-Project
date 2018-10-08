package home.oleg.placesnearme.mapper;

import home.oleg.placenearme.models.Icon;
import home.oleg.placesnearme.network.models.Category;
import home.oleg.placesnearme.network.models.CategoryIcon;

public class IconMapper {

    public static Icon map(CategoryIcon categoryIcon) {
        Icon icon = new Icon();
        icon.setPrefix(categoryIcon.getPrefix());
        icon.setSuffix(categoryIcon.getSuffix());

        return icon;
    }

}
