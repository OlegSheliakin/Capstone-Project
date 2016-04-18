package home.oleg.placesnearme.mapMVP;

import android.location.Location;

import java.util.List;
import java.util.Map;

import home.oleg.placesnearme.Parameters;
import home.oleg.placesnearme.retrofit_models.Item;

/**
 * Created by Oleg on 18.04.2016.
 */
public interface IMapInteractor {
    void sendRequest(Map<String, String> parameters);
}
