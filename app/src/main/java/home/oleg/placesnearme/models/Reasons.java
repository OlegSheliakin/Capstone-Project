
package home.oleg.placesnearme.models;

import java.util.ArrayList;
import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

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
