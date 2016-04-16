
package home.oleg.placesnearme.retrofit_models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ReasonItem {

    @SerializedName("summary")
    @Expose
    private String summary;
    @SerializedName("type")
    @Expose
    private String type;
    @SerializedName("reasonName")
    @Expose
    private String reasonName;

    /**
     * 
     * @return
     *     The summary
     */
    public String getSummary() {
        return summary;
    }

    /**
     * 
     * @param summary
     *     The summary
     */
    public void setSummary(String summary) {
        this.summary = summary;
    }

    /**
     * 
     * @return
     *     The type
     */
    public String getType() {
        return type;
    }

    /**
     * 
     * @param type
     *     The type
     */
    public void setType(String type) {
        this.type = type;
    }

    /**
     * 
     * @return
     *     The reasonName
     */
    public String getReasonName() {
        return reasonName;
    }

    /**
     * 
     * @param reasonName
     *     The reasonName
     */
    public void setReasonName(String reasonName) {
        this.reasonName = reasonName;
    }

}
