package home.oleg.placesnearme.feature_map.di

import java.util.HashMap

import dagger.Module
import dagger.Provides
import home.oleg.placenearme.models.Section
import home.oleg.placesnearme.feature_map.R
import io.reactivex.annotations.NonNull

/**
 * Created by Oleg Sheliakin on 28.08.2018.
 * Contact me by email - olegsheliakin@gmail.com
 */
@Module
class ResourceModule {

    @Provides
    internal fun provideColorMap(): Map<Section, Int> {
        val colors = HashMap<Section, Int>()
        colors[Section.TRENDING] = R.color.colorCategoryTrending
        colors[Section.ARTS] = R.color.colorCategoryArts
        colors[Section.COFFEE] = R.color.colorCategoryCoffee
        colors[Section.DRINKS] = R.color.colorCategoryDrinks
        colors[Section.FOOD] = R.color.colorCategoryFood
        colors[Section.OUTDOORS] = R.color.colorCategoryOutdoors
        colors[Section.SIGHTS] = R.color.colorCategorySights
        colors[Section.SHOPS] = R.color.colorCategoryShops
        colors[Section.TOP] = R.color.colorCategoryTopPicks
        return colors
    }
}
