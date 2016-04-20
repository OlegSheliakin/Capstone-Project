package home.oleg.placesnearme.models;

/**
 * Created by Oleg on 16.04.2016.
 */
public class Notifications {

    private String type;
    private NotificationItem item;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public NotificationItem getItem() {
        return item;
    }

    public void setItem(NotificationItem item) {
        this.item = item;
    }


}
