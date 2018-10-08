package com.smedialink.feature_main.di;

import com.smedialink.feature_main.view.MainActivity;

import dagger.Component;
import home.oleg.placesnearme.scopes.FeatureScope;

@FeatureScope
@Component
public interface MainActivityComponent {

    void inject(MainActivity target);

}
