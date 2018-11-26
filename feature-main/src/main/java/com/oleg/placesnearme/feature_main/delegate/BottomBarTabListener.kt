package com.oleg.placesnearme.feature_main.delegate

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.aurelhubert.ahbottomnavigation.AHBottomNavigation
import com.oleg.placesnearme.feature_main.R
import home.oleg.placesnearme.favoritevenues.presentation.ui.FavoritePlacesFragment
import home.oleg.placesnearme.feature_map.presentation.MapFragment
import home.oleg.placesnearme.venueshistory.presentation.ui.VenuesHistoryFragment
import javax.inject.Inject

class BottomBarTabListener
@Inject constructor(private val fragmentManager: FragmentManager)
    : AHBottomNavigation.OnTabSelectedListener {

    override fun onTabSelected(position: Int, wasSelected: Boolean): Boolean {
        if (wasSelected) {
            return false
        }

        val tag = position.toString()
        val fragmentTransaction = fragmentManager.beginTransaction()

        val curFrag = fragmentManager.findFragmentById(R.id.container)
        if (curFrag != null) {
            if (curFrag is MapFragment) {
                fragmentTransaction.hide(curFrag)
            } else {
                fragmentTransaction.detach(curFrag)
            }
        }

        val curFragment = fragmentManager.findFragmentByTag(tag)
        if (curFragment == null) {
            val fragment = createFrag(position)
            fragmentTransaction.add(R.id.container, fragment, tag)
        } else if (curFragment is MapFragment) {
            fragmentTransaction.show(curFragment)
        } else {
            fragmentTransaction.attach(curFragment)
        }

        fragmentTransaction.commit()
        return true
    }

    private fun createFrag(position: Int): Fragment = when (position) {
        0 -> VenuesHistoryFragment()
        1 -> MapFragment()
        2 -> FavoritePlacesFragment()
        else -> MapFragment()
    }
}
