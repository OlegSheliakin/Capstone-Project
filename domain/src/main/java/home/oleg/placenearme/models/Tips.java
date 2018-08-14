
package home.oleg.placenearme.models;

import java.util.List;

public class Tips {

    private Long count;
    private List<Group<Tip>> groups;

    public Long getCount() {
        return count;
    }

    public void setCount(Long count) {
        this.count = count;
    }

    public List<Group<Tip>> getGroups() {
        return groups;
    }

    public void setGroups(List<Group<Tip>> groups) {
        this.groups = groups;
    }

}
