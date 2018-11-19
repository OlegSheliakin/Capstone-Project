package com.oleg.placesnearme.feature_main.di

import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentManager
import com.oleg.placesnearme.feature_main.view.MainActivity
import dagger.Module
import dagger.Provides

@Module
class MainActivityModule {

    @Provides
    fun provideFragmentManager(mainActivity: MainActivity): FragmentManager {
        return mainActivity.supportFragmentManager
    }

    @Provides
    fun provideAppCompatActivity(mainActivity: MainActivity): AppCompatActivity {
        return mainActivity
    }

}
