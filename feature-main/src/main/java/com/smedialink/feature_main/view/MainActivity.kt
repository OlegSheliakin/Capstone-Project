package com.smedialink.feature_main.view

import android.os.Bundle
import android.view.View

import com.aurelhubert.ahbottomnavigation.AHBottomNavigation
import com.smedialink.feature_main.R
import com.smedialink.feature_main.delegate.BackPressedDelegate
import com.smedialink.feature_main.delegate.BottomBarDelegate
import com.smedialink.feature_main.di.MainActivityComponent
import com.smedialink.feature_venue_detail.view.VenueFragment

import javax.inject.Inject

import androidx.appcompat.app.AppCompatActivity
import com.smedialink.feature_main.viewmodel.MainViewModel
import home.oleg.placesnearme.core_presentation.ShowHideBottomBarListener
import home.oleg.placesnearme.core_presentation.extensions.observe
import home.oleg.placesnearme.core_presentation.viewdata.VenueViewData

class MainActivity : AppCompatActivity(), ShowHideBottomBarListener {

    @Inject
    lateinit var bottomBarDelegate: BottomBarDelegate

    @Inject
    lateinit var backPressedDelegate: BackPressedDelegate

    lateinit var venueFragment: VenueFragment

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        injectDependencies()
        setContentView(R.layout.activity_main)

        venueFragment = supportFragmentManager.findFragmentById(R.id.venueFragment) as VenueFragment
        initBottomBar(savedInstanceState)
    }

    override fun showBottomBar() {
        bottomBarDelegate.showBottomBar()
    }

    override fun hideBottomBar() {
        bottomBarDelegate.hideBottomBar()
    }

    override fun showVenueDetail(venueViewData: VenueViewData) {
        venueFragment.showVenue(venueViewData.id)
    }

    private fun injectDependencies() {
        MainActivityComponent.Injector.inject(this)
    }

    private fun initBottomBar(savedInstanceState: Bundle?) {
        val bottomNavigation = findViewById<AHBottomNavigation>(R.id.bottomNavigationBar)
        val containerBottomBar = findViewById<View>(R.id.containerBottomBar)
        bottomBarDelegate.attach(containerBottomBar, bottomNavigation, savedInstanceState)
    }

    override fun onBackPressed() {
        if (venueFragment.isShown) {
            venueFragment.dismiss()
            return
        }

        if (!backPressedDelegate.onBackPressed()) {
            super.onBackPressed()
        }
    }
}
