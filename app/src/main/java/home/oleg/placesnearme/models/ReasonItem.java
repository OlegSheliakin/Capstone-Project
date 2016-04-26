
package home.oleg.placesnearme.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ReasonItem {

    private String summary;
    private String type;
    private String reasonName;

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getReasonName() {
        return reasonName;
    }

    public void setReasonName(String reasonName) {
        this.reasonName = reasonName;
    }

}
