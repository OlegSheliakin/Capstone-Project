
package home.oleg.placesnearme.network.models;

import java.util.List;

public class Hours {

    private Boolean isLocalHoliday;
    private Boolean isOpen;
    private String status;
    private List<Timeframe> timeframes;

    public Boolean getIsLocalHoliday() {
        return isLocalHoliday;
    }

    public void setIsLocalHoliday(Boolean isLocalHoliday) {
        this.isLocalHoliday = isLocalHoliday;
    }

    public Boolean getIsOpen() {
        return isOpen;
    }

    public void setIsOpen(Boolean isOpen) {
        this.isOpen = isOpen;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<Timeframe> getTimeframes() {
        return timeframes;
    }

    public void setTimeframes(List<Timeframe> timeframes) {
        this.timeframes = timeframes;
    }

}
