package com.smedialink.feature_main.di;

import android.support.annotation.NonNull;

import com.smedialink.feature_main.view.BottomBarTabListener;
import com.smedialink.feature_main.view.MainActivity;

import dagger.Module;
import dagger.Provides;

@Module
public class MainActivityModule {

    @Provides
    @NonNull
    public BottomBarTabListener provideBottomBarTabListener(MainActivity mainActivity) {
        return new BottomBarTabListener(mainActivity.getSupportFragmentManager());
    }
}
