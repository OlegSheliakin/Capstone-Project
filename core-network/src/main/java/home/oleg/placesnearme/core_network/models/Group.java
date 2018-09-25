
package home.oleg.placesnearme.core_network.models;

import java.util.Collections;
import java.util.List;

import io.reactivex.annotations.NonNull;

public class Group<I> {

    private Long count;
    private List<I> items;
    private String name;
    private String summary;
    private String type;

    public Long getCount() {
        return count;
    }

    public void setCount(Long count) {
        this.count = count;
    }

    @NonNull
    public List<I> getItems() {
        if (items == null) {
            return Collections.emptyList();
        }
        return items;
    }

    public void setItems(@NonNull List<I> items) {
        this.items = items;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

}
