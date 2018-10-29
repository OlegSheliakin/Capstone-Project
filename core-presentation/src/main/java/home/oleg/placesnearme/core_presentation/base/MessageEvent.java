package home.oleg.placesnearme.core_presentation.base;

import android.support.annotation.NonNull;

public class MessageEvent extends Event {

    private final String text;

    public MessageEvent(@NonNull String text) {
        this.text = text;
    }

    @NonNull
    public String getText() {
        return text;
    }
}

