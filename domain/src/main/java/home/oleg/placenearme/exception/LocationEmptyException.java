package home.oleg.placenearme.exception;

/**
 * Created by Oleg Sheliakin on 17.09.2018.
 * Contact me by email - olegsheliakin@gmail.com
 */
public class LocationEmptyException extends Exception {

    public LocationEmptyException() {
        super("Location is null");
    }
}
