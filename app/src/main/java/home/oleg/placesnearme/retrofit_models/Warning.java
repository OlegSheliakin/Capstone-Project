
package home.oleg.placesnearme.retrofit_models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Warning {

    @SerializedName("text")
    @Expose
    private String text;

    /**
     * 
     * @return
     *     The text
     */
    public String getText() {
        return text;
    }

    /**
     * 
     * @param text
     *     The text
     */
    public void setText(String text) {
        this.text = text;
    }

}
