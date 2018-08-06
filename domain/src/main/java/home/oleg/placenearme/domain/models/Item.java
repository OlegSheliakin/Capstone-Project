
package home.oleg.placenearme.domain.models;

import java.util.ArrayList;
import java.util.List;

public class Item {

    private Reasons reasons;
    private Venue venue;
    private List<Tip> tips = new ArrayList<>();
    private String referralId;

    public Reasons getReasons() {
        return reasons;
    }

    public void setReasons(Reasons reasons) {
        this.reasons = reasons;
    }

    public Venue getVenue() {
        return venue;
    }

    public void setVenue(Venue venue) {
        this.venue = venue;
    }

    public List<Tip> getTips() {
        return tips;
    }

    public void setTips(List<Tip> tips) {
        this.tips = tips;
    }

    public String getReferralId() {
        return referralId;
    }

    public void setReferralId(String referralId) {
        this.referralId = referralId;
    }

}
