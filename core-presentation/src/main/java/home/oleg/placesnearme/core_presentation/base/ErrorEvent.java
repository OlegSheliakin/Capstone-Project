package home.oleg.placesnearme.core_presentation.base;

/**
 * Created by Oleg Sheliakin on 27.10.2018.
 * Contact me by email - olegsheliakin@gmail.com
 */
public class ErrorEvent extends Event {

    private final Throwable error;
    private final String errorText;

    public ErrorEvent(Throwable error, String errorText) {
        this.error = error;
        this.errorText = errorText;
    }

    public Throwable getError() {
        return error;
    }

    public String getErrorText() {
        return errorText;
    }
}
