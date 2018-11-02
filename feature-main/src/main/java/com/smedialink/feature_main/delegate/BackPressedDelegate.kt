package com.smedialink.feature_main.delegate

import com.smedialink.feature_main.R

import javax.inject.Inject

import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import home.oleg.placesnearme.core_presentation.base.BackHandler

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
