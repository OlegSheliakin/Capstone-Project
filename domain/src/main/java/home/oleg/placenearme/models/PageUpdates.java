
package home.oleg.placenearme.models;

import java.util.List;

public class PageUpdates {

    private Long count;
    private List<Tip> items;

    public Long getCount() {
        return count;
    }

    public void setCount(Long count) {
        this.count = count;
    }

    public List<Tip> getItems() {
        return items;
    }

    public void setItems(List<Tip> items) {
        this.items = items;
    }

}
