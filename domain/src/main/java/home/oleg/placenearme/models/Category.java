
package home.oleg.placenearme.models;

public final class Category {

    private String name;
    private String iconPrefix;
    private String iconSuffix;

    public String getIconPrefix() {
        return iconPrefix;
    }

    public void setIconPrefix(String iconPrefix) {
        this.iconPrefix = iconPrefix;
    }

    public String getIconSuffix() {
        return iconSuffix;
    }

    public void setIconSuffix(String iconSuffix) {
        this.iconSuffix = iconSuffix;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
