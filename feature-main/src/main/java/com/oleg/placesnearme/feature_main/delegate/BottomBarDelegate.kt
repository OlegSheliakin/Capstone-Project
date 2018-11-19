package com.oleg.placesnearme.feature_main.delegate

import android.graphics.Color
import android.os.Bundle
import android.view.View
import com.aurelhubert.ahbottomnavigation.AHBottomNavigation
import com.aurelhubert.ahbottomnavigation.AHBottomNavigationItem
import com.oleg.placesnearme.feature_main.R
import home.oleg.placesnearme.core_presentation.api.ShowHideBottomBar
import javax.inject.Inject

/**
 * Created by Oleg Sheliakin on 04.09.2018.
 * Contact me by email - olegsheliakin@gmail.com
 */
class BottomBarDelegate @Inject
constructor(private val bottomBarTabListener: BottomBarTabListener) : ShowHideBottomBar {

    private var containerBottomBar: View? = null

    fun attach(containerBottomBar: View, bottomNavigation: AHBottomNavigation, savedInstanceState: Bundle?) {
        this.containerBottomBar = containerBottomBar
        bottomNavigation.accentColor = Color.WHITE
        bottomNavigation.inactiveColor = Color.WHITE
        bottomNavigation.defaultBackgroundColor = Color.TRANSPARENT
        bottomNavigation.titleState = AHBottomNavigation.TitleState.ALWAYS_HIDE

        val item1 = AHBottomNavigationItem(null, R.drawable.fab_location_pin_state_drawable)
        val item2 = AHBottomNavigationItem(null, R.drawable.button_map)
        val item3 = AHBottomNavigationItem(null, R.drawable.fab_favorite_state_drawable)

        bottomNavigation.addItem(item1)
        bottomNavigation.addItem(item2)
        bottomNavigation.addItem(item3)

        bottomNavigation.setOnTabSelectedListener(bottomBarTabListener)

        if (savedInstanceState == null) {
            bottomNavigation.currentItem = 1
        }
    }

    override fun showBottomBar() {
        containerBottomBar?.takeIf { it.translationY == 0f }?.let {
            it.animate().translationY(it.height.toFloat()).start()
        }
    }

    override fun hideBottomBar() {
        containerBottomBar?.takeIf { it.translationY > 0f }?.let {
            it.animate().translationY(0f).start()
        }
    }

}
