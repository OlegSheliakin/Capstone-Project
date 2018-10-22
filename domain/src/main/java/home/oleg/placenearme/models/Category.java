
package home.oleg.placenearme.models;

import java.util.Objects;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Category category = (Category) o;
        return Objects.equals(name, category.name) &&
                Objects.equals(iconPrefix, category.iconPrefix) &&
                Objects.equals(iconSuffix, category.iconSuffix);
    }

    @Override
    public int hashCode() {

        return Objects.hash(name, iconPrefix, iconSuffix);
    }
}
