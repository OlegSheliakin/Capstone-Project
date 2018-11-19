package com.oleg.placesnearme.feature_main.delegate

import androidx.appcompat.app.AppCompatActivity
import com.oleg.placesnearme.feature_main.R
import com.smedialink.common.base.BackHandler
import javax.inject.Inject

class BackPressedDelegate @Inject
constructor(private val appCompatActivity: AppCompatActivity) : OnBackPressListener {

    override fun onBackPressed(): Boolean {
        val curFrag = appCompatActivity.supportFragmentManager.findFragmentById(R.id.container)
        var intercepted = false

        if (curFrag is BackHandler) {
            intercepted = (curFrag as BackHandler).onBackPressed()
        }

        return intercepted

    }

}
