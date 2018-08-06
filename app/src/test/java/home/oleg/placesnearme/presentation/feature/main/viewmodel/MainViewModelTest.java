package home.oleg.placesnearme.presentation.feature.main.viewmodel;

import org.junit.Test;

import home.oleg.placesnearme.presentation.feature.base.Action;

import static org.junit.Assert.*;

public class MainViewModelTest {

    private final MainViewModel mainViewModel = new MainViewModel(new Action<String>() {
        @Override
        public void perform(String s) {

        }
    });

    @Test
    public void act() {
        mainViewModel.act("Hello");
    }

}