
package home.oleg.placenearme.models;

public class BeenHere {

    private Long count;
    private Long lastCheckinExpiredAt;
    private Boolean marked;
    private Long unconfirmedCount;

    public Long getCount() {
        return count;
    }

    public void setCount(Long count) {
        this.count = count;
    }

    public Long getLastCheckinExpiredAt() {
        return lastCheckinExpiredAt;
    }

    public void setLastCheckinExpiredAt(Long lastCheckinExpiredAt) {
        this.lastCheckinExpiredAt = lastCheckinExpiredAt;
    }

    public Boolean getMarked() {
        return marked;
    }

    public void setMarked(Boolean marked) {
        this.marked = marked;
    }

    public Long getUnconfirmedCount() {
        return unconfirmedCount;
    }

    public void setUnconfirmedCount(Long unconfirmedCount) {
        this.unconfirmedCount = unconfirmedCount;
    }

}
