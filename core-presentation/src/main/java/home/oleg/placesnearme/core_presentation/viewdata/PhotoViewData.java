package home.oleg.placesnearme.core_presentation.viewdata;

import java.util.Objects;

import home.oleg.placenearme.models.Photo;

/**
 * Created by Oleg Sheliakin on 21.09.2018.
 * Contact me by email - olegsheliakin@gmail.com
 */
public class PhotoViewData {

    private Long height;
    private String prefix;
    private String suffix;
    private Long width;

    public PhotoViewData() {

    }

    public Long getHeight() {
        return height;
    }

    public String getPrefix() {
        return prefix;
    }

    public String getSuffix() {
        return suffix;
    }

    public Long getWidth() {
        return width;
    }

    public String getFullSizeUrl() {
        return prefix + width + "x" + height + suffix;
    }

    public void setHeight(Long height) {
        this.height = height;
    }

    public void setPrefix(String prefix) {
        this.prefix = prefix;
    }

    public void setSuffix(String suffix) {
        this.suffix = suffix;
    }

    public void setWidth(Long width) {
        this.width = width;
    }

    public static PhotoViewData map(Photo photo) {
        PhotoViewData photoViewData = new PhotoViewData();
        photoViewData.setHeight(photo.getHeight());
        photoViewData.setPrefix(photo.getPrefix());
        photoViewData.setWidth(photo.getWidth());
        photoViewData.setSuffix(photo.getSuffix());
        return photoViewData;
    }

    public static Photo map(PhotoViewData photoViewData) {
        Photo photo = new Photo();
        photo.setHeight(photoViewData.getHeight());
        photo.setPrefix(photoViewData.getPrefix());
        photo.setWidth(photoViewData.getWidth());
        photo.setSuffix(photoViewData.getSuffix());
        return photo;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PhotoViewData that = (PhotoViewData) o;
        return Objects.equals(height, that.height) &&
                Objects.equals(prefix, that.prefix) &&
                Objects.equals(suffix, that.suffix) &&
                Objects.equals(width, that.width);
    }

    @Override
    public int hashCode() {

        return Objects.hash(height, prefix, suffix, width);
    }
}
