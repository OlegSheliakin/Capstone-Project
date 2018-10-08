
package home.oleg.placesnearme.network.models;

import java.util.List;

public class Photos {

    private Long count;

    private List<Photo> items;

    public Long getCount() {
        return count;
    }

    public void setCount(Long count) {
        this.count = count;
    }

    public List<Photo> getItems() {
        return items;
    }

    public void setItems(List<Photo> items) {
        this.items = items;
    }

}
