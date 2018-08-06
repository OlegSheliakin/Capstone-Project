
package home.oleg.placenearme.domain.models;

import java.util.ArrayList;
import java.util.List;

public class Reasons {

    private Integer count;
    private List<ReasonItem> items = new ArrayList<>();

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public List<ReasonItem> getItems() {
        return items;
    }

    public void setItems(List<ReasonItem> items) {
        this.items = items;
    }

}
