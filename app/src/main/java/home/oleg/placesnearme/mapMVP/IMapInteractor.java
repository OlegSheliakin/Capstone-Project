package home.oleg.placesnearme.mapMVP;

import java.util.Map;

/**
 * Created by Oleg on 18.04.2016.
 */
public interface IMapInteractor {
    void sendRequest(Map<String, String> parameters);
}
