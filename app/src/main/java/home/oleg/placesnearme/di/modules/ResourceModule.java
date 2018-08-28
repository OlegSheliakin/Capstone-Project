package home.oleg.placesnearme.di.modules;

import java.util.HashMap;
import java.util.Map;

import dagger.Module;
import dagger.Provides;
import home.oleg.placenearme.repositories.Section;
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
    Map<Section, Integer> provideColorMap() {
        Map<Section, Integer> colors = new HashMap<>();
        colors.put(Section.TRENDING, R.color.colorCategoryTrending);
        colors.put(Section.ARTS, R.color.colorCategoryArts);
        colors.put(Section.COFFEE, R.color.colorCategoryCoffee);
        colors.put(Section.DRINKS, R.color.colorCategoryDrinks);
        colors.put(Section.FOOD, R.color.colorCategoryFood);
        colors.put(Section.OUTDOORS, R.color.colorCategoryOutdoors);
        colors.put(Section.SIGHTS, R.color.colorCategorySights);
        colors.put(Section.SHOPS, R.color.colorCategoryShops);
        colors.put(Section.TOP, R.color.colorCategoryTopPicks);
        return colors;
    }
}
