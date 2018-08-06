
package home.oleg.placenearme.domain.models;

import java.util.ArrayList;
import java.util.List;

public class VenuesPhoto {

    private Integer count;
    private List<Object> groups = new ArrayList<>();

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public List<Object> getGroups() {
        return groups;
    }

    public void setGroups(List<Object> groups) {
        this.groups = groups;
    }

}
