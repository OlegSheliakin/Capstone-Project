
package home.oleg.placenearme.domain.models;

import java.util.ArrayList;
import java.util.List;

public class Group {

    private String type;
    private String name;
    private List<Item> items = new ArrayList<>();

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Item> getItems() {
        return items;
    }

    public void setItems(List<Item> items) {
        this.items = items;
    }

}
