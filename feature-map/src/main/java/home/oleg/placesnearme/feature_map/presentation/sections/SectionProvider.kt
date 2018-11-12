package home.oleg.placesnearme.feature_map.presentation.sections

import java.util.ArrayList
import java.util.Collections

import home.oleg.placenearme.models.Section
import home.oleg.placesnearme.feature_map.R

/**
 * Created by Oleg Sheliakin on 25.10.2018.
 * Contact me by email - olegsheliakin@gmail.com
 */
class SectionProvider {
    private val sectionViewDataList: MutableList<SectionViewData>

    val sections: List<SectionViewData>
        get() = Collections.unmodifiableList(sectionViewDataList)

    init {
        sectionViewDataList = ArrayList()
        sectionViewDataList.add(
                SectionViewData(Section.TOP, R.string.label_section_top, R.drawable.state_top_section))
        sectionViewDataList.add(
                SectionViewData(Section.ARTS, R.string.label_section_arts, R.drawable.state_arts_section))
        sectionViewDataList.add(
                SectionViewData(Section.COFFEE, R.string.label_section_coffee, R.drawable.state_coffee_section))
        sectionViewDataList.add(
                SectionViewData(Section.DRINKS, R.string.label_section_drinks, R.drawable.state_drinks_section))
        sectionViewDataList.add(
                SectionViewData(Section.FOOD, R.string.label_section_food, R.drawable.state_food_section))
        sectionViewDataList.add(
                SectionViewData(Section.OUTDOORS, R.string.label_section_outdoors, R.drawable.state_outdoors_section))
        sectionViewDataList.add(
                SectionViewData(Section.SHOPS, R.string.label_section_shops, R.drawable.state_shops_section))
        sectionViewDataList.add(
                SectionViewData(Section.SIGHTS, R.string.label_section_sights, R.drawable.state_sights_section))
        sectionViewDataList.add(
                SectionViewData(Section.TRENDING, R.string.label_section_trending, R.drawable.state_trends_section))
    }
}
