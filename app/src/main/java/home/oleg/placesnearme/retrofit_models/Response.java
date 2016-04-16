
package home.oleg.placesnearme.retrofit_models;

import java.util.ArrayList;
import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Response {

    @SerializedName("warning")
    @Expose
    private Warning warning;
    @SerializedName("headerLocation")
    @Expose
    private String headerLocation;
    @SerializedName("headerFullLocation")
    @Expose
    private String headerFullLocation;
    @SerializedName("headerLocationGranularity")
    @Expose
    private String headerLocationGranularity;
    @SerializedName("query")
    @Expose
    private String query;
    @SerializedName("totalResults")
    @Expose
    private Integer totalResults;
    @SerializedName("suggestedBounds")
    @Expose
    private SuggestedBounds suggestedBounds;
    @SerializedName("groups")
    @Expose
    private List<Group> groups = new ArrayList<Group>();

    /**
     * 
     * @return
     *     The warning
     */
    public Warning getWarning() {
        return warning;
    }

    /**
     * 
     * @param warning
     *     The warning
     */
    public void setWarning(Warning warning) {
        this.warning = warning;
    }

    /**
     * 
     * @return
     *     The headerLocation
     */
    public String getHeaderLocation() {
        return headerLocation;
    }

    /**
     * 
     * @param headerLocation
     *     The headerLocation
     */
    public void setHeaderLocation(String headerLocation) {
        this.headerLocation = headerLocation;
    }

    /**
     * 
     * @return
     *     The headerFullLocation
     */
    public String getHeaderFullLocation() {
        return headerFullLocation;
    }

    /**
     * 
     * @param headerFullLocation
     *     The headerFullLocation
     */
    public void setHeaderFullLocation(String headerFullLocation) {
        this.headerFullLocation = headerFullLocation;
    }

    /**
     * 
     * @return
     *     The headerLocationGranularity
     */
    public String getHeaderLocationGranularity() {
        return headerLocationGranularity;
    }

    /**
     * 
     * @param headerLocationGranularity
     *     The headerLocationGranularity
     */
    public void setHeaderLocationGranularity(String headerLocationGranularity) {
        this.headerLocationGranularity = headerLocationGranularity;
    }

    /**
     * 
     * @return
     *     The query
     */
    public String getQuery() {
        return query;
    }

    /**
     * 
     * @param query
     *     The query
     */
    public void setQuery(String query) {
        this.query = query;
    }

    /**
     * 
     * @return
     *     The totalResults
     */
    public Integer getTotalResults() {
        return totalResults;
    }

    /**
     * 
     * @param totalResults
     *     The totalResults
     */
    public void setTotalResults(Integer totalResults) {
        this.totalResults = totalResults;
    }

    /**
     * 
     * @return
     *     The suggestedBounds
     */
    public SuggestedBounds getSuggestedBounds() {
        return suggestedBounds;
    }

    /**
     * 
     * @param suggestedBounds
     *     The suggestedBounds
     */
    public void setSuggestedBounds(SuggestedBounds suggestedBounds) {
        this.suggestedBounds = suggestedBounds;
    }

    /**
     * 
     * @return
     *     The groups
     */
    public List<Group> getGroups() {
        return groups;
    }

    /**
     * 
     * @param groups
     *     The groups
     */
    public void setGroups(List<Group> groups) {
        this.groups = groups;
    }

}
