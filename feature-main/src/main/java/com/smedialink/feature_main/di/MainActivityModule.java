package com.smedialink.feature_main.di;

import android.support.annotation.NonNull;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;

import com.smedialink.feature_main.view.MainActivity;

import dagger.Module;
import dagger.Provides;

@Module
public class MainActivityModule {

    @Provides
    @NonNull
    public FragmentManager provideFragmentManager(MainActivity mainActivity) {
        return mainActivity.getSupportFragmentManager();
    }

    @Provides
    @NonNull
    public AppCompatActivity provideAppCompatActivity(MainActivity mainActivity) {
        return mainActivity;
    }


}
