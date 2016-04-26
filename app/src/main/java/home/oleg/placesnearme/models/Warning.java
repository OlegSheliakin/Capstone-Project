
package home.oleg.placesnearme.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Warning {

    private String text;

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

}
