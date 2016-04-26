
package home.oleg.placesnearme.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class SuggestedBounds {

    private Ne ne;
    private Sw sw;

    public Ne getNe() {
        return ne;
    }

    public void setNe(Ne ne) {
        this.ne = ne;
    }

    public Sw getSw() {
        return sw;
    }

    public void setSw(Sw sw) {
        this.sw = sw;
    }

}
