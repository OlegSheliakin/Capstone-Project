
package home.oleg.placesnearme.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Price {

    private Integer tier;
    private String message;
    private String currency;

    public Integer getTier() {
        return tier;
    }

    public void setTier(Integer tier) {
        this.tier = tier;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

}
