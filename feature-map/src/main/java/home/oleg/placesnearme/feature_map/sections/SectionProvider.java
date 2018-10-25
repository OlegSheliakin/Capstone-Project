package home.oleg.placesnearme.feature_map.mapper;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import home.oleg.placenearme.models.Section;
import home.oleg.placesnearme.feature_map.R;
import home.oleg.placesnearme.feature_map.viewdata.SectionViewData;

/**
 * Created by Oleg Sheliakin on 25.10.2018.
 * Contact me by email - olegsheliakin@gmail.com
 */
public final class SectionProvider {

    private List<SectionViewData> sectionViewDataList;

    {
        sectionViewDataList = new ArrayList<>();
        sectionViewDataList.add(
                new SectionViewData(Section.TOP, R.string.label_section_top, R.drawable.state_top_section));
        sectionViewDataList.add(
                new SectionViewData(Section.ARTS, R.string.label_section_arts, R.drawable.state_top_section));
        sectionViewDataList.add(
                new SectionViewData(Section.COFFEE, R.string.label_section_coffee, R.drawable.state_top_section));
        sectionViewDataList.add(
                new SectionViewData(Section.DRINKS, R.string.label_section_drinks, R.drawable.state_top_section));
        sectionViewDataList.add(
                new SectionViewData(Section.FOOD, R.string.label_section_food, R.drawable.state_top_section));
        sectionViewDataList.add(
                new SectionViewData(Section.OUTDOORS, R.string.label_section_outdoors, R.drawable.state_top_section));
        sectionViewDataList.add(
                new SectionViewData(Section.SHOPS, R.string.label_section_shops, R.drawable.state_top_section));
        sectionViewDataList.add(
                new SectionViewData(Section.SIGHTS, R.string.label_section_sights, R.drawable.state_top_section));
        sectionViewDataList.add(
                new SectionViewData(Section.TRENDING, R.string.label_section_trending, R.drawable.state_top_section));
    }

    public List<SectionViewData> getSections() {
        return Collections.unmodifiableList(sectionViewDataList);
    }
}
