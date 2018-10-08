package home.oleg.placesnearme.mapper;

import java.util.ArrayList;
import java.util.List;

import home.oleg.placenearme.models.Category;

/**
 * Created by Oleg Sheliakin on 25.09.2018.
 * Contact me by email - olegsheliakin@gmail.com
 */
public final class CategoryMapper {

    private CategoryMapper() {
    }

    public static Category map(home.oleg.placesnearme.network.models.Category category) {
        Category result = new Category();
        result.setId(category.getId());
        result.setName(category.getName());
        result.setPluralName(category.getPluralName());
        result.setShortName(category.getShortName());

        if (category.getIcon() != null) {
            result.setIcon(IconMapper.map(category.getIcon()));
        }
        return result;
    }

    public static List<Category> map(List<home.oleg.placesnearme.network.models.Category> categories) {
        List<Category> result = new ArrayList<>();

        for (home.oleg.placesnearme.network.models.Category category : categories) {
            result.add(map(category));
        }

        return result;
    }

}
