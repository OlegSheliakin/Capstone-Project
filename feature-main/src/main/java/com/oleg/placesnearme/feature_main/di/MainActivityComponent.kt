package com.oleg.placesnearme.feature_main.di

import com.oleg.placesnearme.feature_main.view.MainActivity

import dagger.BindsInstance
import dagger.Component
import home.oleg.placesnearme.coredi.scopes.FeatureScope

@FeatureScope
@Component(modules = [MainActivityModule::class])
interface MainActivityComponent {

    fun inject(target: MainActivity)

    @Component.Builder
    interface Builder {
        @BindsInstance
        fun bind(target: MainActivity): MainActivityComponent.Builder

        fun build(): MainActivityComponent
    }

    object Injector {

        fun inject(activity: MainActivity) {
            DaggerMainActivityComponent.builder()
                    .bind(activity)
                    .build()
                    .inject(activity)
        }
    }
}
