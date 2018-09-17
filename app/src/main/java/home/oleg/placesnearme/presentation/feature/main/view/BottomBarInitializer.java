package home.oleg.placesnearme.presentation.feature.main.view;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.IdRes;
import android.support.v4.content.ContextCompat;

import com.aurelhubert.ahbottomnavigation.AHBottomNavigation;
import com.aurelhubert.ahbottomnavigation.AHBottomNavigationItem;

import javax.inject.Inject;

import home.oleg.placesnearme.R;
import home.oleg.placesnearme.common.provider.ResourceProvider;

/**
 * Created by Oleg Sheliakin on 04.09.2018.
 * Contact me by email - olegsheliakin@gmail.com
 */
public class BottomBarInitializer {

    @Inject
    public BottomBarInitializer() {

    }

    public void initialize(AHBottomNavigation bottomNavigation) {
        bottomNavigation.setAccentColor(Color.WHITE);
        bottomNavigation.setInactiveColor(Color.WHITE);
        bottomNavigation.setDefaultBackgroundColor(Color.TRANSPARENT);
        bottomNavigation.setTitleState(AHBottomNavigation.TitleState.ALWAYS_HIDE);

        AHBottomNavigationItem item1 = new AHBottomNavigationItem(null, R.drawable.ic_place);
        AHBottomNavigationItem item2 = new AHBottomNavigationItem(null, R.drawable.button_map);
        AHBottomNavigationItem item3 = new AHBottomNavigationItem(null, R.drawable.ic_add_favorite);
        bottomNavigation.addItem(item1);
        bottomNavigation.addItem(item2);
        bottomNavigation.addItem(item3);
    }

}
