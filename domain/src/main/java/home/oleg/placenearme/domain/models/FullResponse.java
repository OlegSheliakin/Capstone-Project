
package home.oleg.placenearme.domain.models;

import java.util.ArrayList;
import java.util.List;

public class FullResponse{

    private Meta meta;
    private Response response;
    private List<Notifications> notifications = new ArrayList<>();

    public List<Notifications> getNotifications() {
        return notifications;
    }

    public void setNotifications(List<Notifications> notifications) {
        this.notifications = notifications;
    }

    public Meta getMeta() {
        return meta;
    }

    public void setMeta(Meta meta) {
        this.meta = meta;
    }

    public Response getResponse() {
        return response;
    }

    public void setResponse(Response response) {
        this.response = response;
    }

}
