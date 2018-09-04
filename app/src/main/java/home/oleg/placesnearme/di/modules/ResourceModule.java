package home.oleg.placesnearme.di.modules;

import java.util.HashMap;
import java.util.Map;

import dagger.Module;
import dagger.Provides;
import home.oleg.placenearme.models.Section;
import home.oleg.placesnearme.R;
import io.reactivex.annotations.NonNull;

/**
 * Created by Oleg Sheliakin on 28.08.2018.
 * Contact me by email - olegsheliakin@gmail.com
 */
@Module
public class ResourceModule {

    @Provides
    @NonNull
    Map<Section.Type, Integer> provideColorMap() {
        Map<Section.Type, Integer> colors = new HashMap<>();
        colors.put(Section.Type.TRENDING, R.color.colorCategoryTrending);
        colors.put(Section.Type.ARTS, R.color.colorCategoryArts);
        colors.put(Section.Type.COFFEE, R.color.colorCategoryCoffee);
        colors.put(Section.Type.DRINKS, R.color.colorCategoryDrinks);
        colors.put(Section.Type.FOOD, R.color.colorCategoryFood);
        colors.put(Section.Type.OUTDOORS, R.color.colorCategoryOutdoors);
        colors.put(Section.Type.SIGHTS, R.color.colorCategorySights);
        colors.put(Section.Type.SHOPS, R.color.colorCategoryShops);
        colors.put(Section.Type.TOP, R.color.colorCategoryTopPicks);
        return colors;
    }
}
