
package home.oleg.placesnearme.models.responses;

import java.util.List;

import home.oleg.placenearme.models.Tip;

public class TipsResponse {

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
