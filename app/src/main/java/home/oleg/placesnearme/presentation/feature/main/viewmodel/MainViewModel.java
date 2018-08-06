package home.oleg.placesnearme.presentation.feature.main.viewmodel;

import android.arch.lifecycle.ViewModel;

import home.oleg.placesnearme.presentation.feature.base.Action;

public class MainViewModel extends ViewModel {

    private final Action<String> action;

    public MainViewModel(Action<String> action) {
        this.action = action;
    }

    public void act(String text) {
        action.perform(text);
    }
}
