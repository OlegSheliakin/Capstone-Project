
package home.oleg.placesnearme.network.models;

import java.util.List;

public class PhotosGroup {

    private Long count;
    private List<Group<Photo>> groups;

    public Long getCount() {
        return count;
    }

    public void setCount(Long count) {
        this.count = count;
    }

    public List<Group<Photo>> getGroups() {
        return groups;
    }

    public void setGroups(List<Group<Photo>> groups) {
        this.groups = groups;
    }

}
