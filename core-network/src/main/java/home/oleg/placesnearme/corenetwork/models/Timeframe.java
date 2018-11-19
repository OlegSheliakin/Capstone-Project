
package home.oleg.placesnearme.corenetwork.models;

import java.util.List;

public class Timeframe {

    private String days;
    private Boolean includesToday;
    private List<Open> open;

    public String getDays() {
        return days;
    }

    public void setDays(String days) {
        this.days = days;
    }

    public Boolean getIncludesToday() {
        return includesToday;
    }

    public void setIncludesToday(Boolean includesToday) {
        this.includesToday = includesToday;
    }

    public List<Open> getOpen() {
        return open;
    }

    public void setOpen(List<Open> open) {
        this.open = open;
    }

}
