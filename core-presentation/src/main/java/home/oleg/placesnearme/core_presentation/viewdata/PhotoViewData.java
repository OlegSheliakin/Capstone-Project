package home.oleg.placesnearme.core_presentation.viewdata;

import android.os.Parcel;
import android.os.Parcelable;

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

}
