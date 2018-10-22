
package home.oleg.placenearme.models;

import java.util.Objects;

public class Photo {

    private Long height;
    private String prefix;
    private String suffix;
    private Long width;

    public Long getHeight() {
        return height;
    }

    public void setHeight(Long height) {
        this.height = height;
    }

    public String getPrefix() {
        return prefix;
    }

    public void setPrefix(String prefix) {
        this.prefix = prefix;
    }

    public String getSuffix() {
        return suffix;
    }

    public void setSuffix(String suffix) {
        this.suffix = suffix;
    }

    public Long getWidth() {
        return width;
    }

    public void setWidth(Long width) {
        this.width = width;
    }

    public String getImageUrl() {
        return prefix + height + "x" + width + suffix;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Photo photo = (Photo) o;
        return Objects.equals(height, photo.height) &&
                Objects.equals(prefix, photo.prefix) &&
                Objects.equals(suffix, photo.suffix) &&
                Objects.equals(width, photo.width);
    }

    @Override
    public int hashCode() {

        return Objects.hash(height, prefix, suffix, width);
    }
}
