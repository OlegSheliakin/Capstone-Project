package com.smedialink.feature_main.delegate;

import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;

import com.smedialink.feature_main.R;

import javax.inject.Inject;

import home.oleg.placesnearme.core_presentation.base.BackHandler;

public class BackPressedDelegate implements OnBackPressListener {

    private final AppCompatActivity appCompatActivity;

    @Inject
    public BackPressedDelegate(AppCompatActivity appCompatActivity) {
        this.appCompatActivity = appCompatActivity;
    }

    @Override
    public boolean onBackPressed() {
        Fragment curFrag = appCompatActivity.getSupportFragmentManager().findFragmentById(R.id.container);
        boolean intercepted = false;

        if (curFrag instanceof BackHandler) {
            intercepted = ((BackHandler) curFrag).onBackPressed();
        }

       return intercepted;

    }

}
