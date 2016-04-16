package home.oleg.placesnearme.models;

/**
 * Created by Oleg on 12.04.2016.
 */
public class Category {

    private String id;

    private String name;

    private String pluralName;

    private String shortName;

    private String[] parents;

    private boolean primary;

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getPluralName() {
        return pluralName;
    }

    public String[] getParents() {
        return parents;
    }

    public boolean isPrimary() {
        return primary;
    }

    public String getShortName() {
        return shortName;
    }

}
