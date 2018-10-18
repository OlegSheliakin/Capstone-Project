package home.oleg.placesnearme.data.mapper;

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
        result.setName(category.getName());
        if (category.getIcon() != null) {
            result.setIconPrefix(category.getIcon().getPrefix());
            result.setIconSuffix(category.getIcon().getSuffix());
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
