package com.oleg.placesnearme.feature_main.view

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.oleg.placesnearme.feature_main.R
import com.oleg.placesnearme.feature_main.delegate.BackPressedDelegate
import com.oleg.placesnearme.feature_main.delegate.BottomBarDelegate
import com.oleg.placesnearme.feature_main.di.MainActivityComponent
import home.oleg.placesnearme.corepresentation.api.ShowHideBottomBar
import home.oleg.placesnearme.corepresentation.api.ShowVenueDetail
import home.oleg.placesnearme.corepresentation.viewdata.PlaceViewData
import home.oleg.placesnearme.feature_venue_detail.presentation.ui.VenueFragment
import kotlinx.android.synthetic.main.bottom_bar.*
import javax.inject.Inject

class MainActivity : AppCompatActivity(), ShowHideBottomBar, ShowVenueDetail {

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

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        supportFragmentManager.fragments.forEach {
            it.onActivityResult(requestCode, resultCode, data)
        }
    }

    override fun showBottomBar() {
        bottomBarDelegate.showBottomBar()
    }

    override fun hideBottomBar() {
        bottomBarDelegate.hideBottomBar()
    }

    override fun showVenueDetail(venueViewData: PlaceViewData) {
        venueFragment.open(venueViewData.id)
    }

    private fun injectDependencies() {
        MainActivityComponent.Injector.inject(this)
    }

    private fun initBottomBar(savedInstanceState: Bundle?) {
        bottomBarDelegate.attach(containerBottomBar, bottomNavigationBar, savedInstanceState)
    }

    override fun onBackPressed() {
        if (venueFragment.dismiss()) {
            return
        }

        if (!backPressedDelegate.onBackPressed()) {
            super.onBackPressed()
        }
    }
}
