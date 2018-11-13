package com.smedialink.feature_main.delegate

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.aurelhubert.ahbottomnavigation.AHBottomNavigation
import com.smedialink.feature_main.R
import home.oleg.feature_favorite_venues.presentation.ui.FavoritePlacesFragment
import home.oleg.placesnearme.feature_map.presentation.ui.VenuesMapFragment
import home.oleg.placesnearme.feature_venues_history.presentation.ui.VenuesHistoryFragment
import javax.inject.Inject

class BottomBarTabListener @Inject
constructor(private val fragmentManager: FragmentManager) : AHBottomNavigation.OnTabSelectedListener {

    override fun onTabSelected(position: Int, wasSelected: Boolean): Boolean {
        if (wasSelected) {
            return false
        }

        val tag = position.toString()
        val fragmentTransaction = fragmentManager.beginTransaction()

        val curFrag = fragmentManager.findFragmentById(R.id.container)
        if (curFrag != null) {
            if (curFrag is VenuesMapFragment) {
                fragmentTransaction.hide(curFrag)
            } else {
                fragmentTransaction.detach(curFrag)
            }
        }

        val curFragment = fragmentManager.findFragmentByTag(tag)
        if (curFragment == null) {
            val fragment: Fragment
            when (position) {
                0 -> fragment = VenuesHistoryFragment()
                1 -> fragment = VenuesMapFragment()
                2 -> fragment = FavoritePlacesFragment()
                else -> fragment = VenuesMapFragment()
            }
            fragmentTransaction.add(R.id.container, fragment, tag)
        } else if (curFragment is VenuesMapFragment) {
            fragmentTransaction.show(curFragment)
        } else {
            fragmentTransaction.attach(curFragment)
        }

        fragmentTransaction.commit()
        return true
    }
}
