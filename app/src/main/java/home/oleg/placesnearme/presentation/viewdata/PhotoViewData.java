package home.oleg.placesnearme.presentation.viewdata;

import android.os.Parcel;
import android.os.Parcelable;

import home.oleg.placenearme.models.Photo;

/**
 * Created by Oleg Sheliakin on 21.09.2018.
 * Contact me by email - olegsheliakin@gmail.com
 */
public class PhotoViewData implements Parcelable {

    private Long height;
    private String prefix;
    private String suffix;
    private Long width;

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

    public String getIconUrl() {
        return prefix + width + "x" + height + suffix;
    }

    public String getGrayIconUrl() {
        return prefix + width + "x" + height + suffix;
    }

    public static PhotoViewData map(Photo photo) {
        PhotoViewData photoViewData = new PhotoViewData();
        photoViewData.setHeight(photo.getHeight());
        photoViewData.setPrefix(photo.getPrefix());
        photoViewData.setWidth(photo.getWidth());
        photoViewData.setSuffix(photo.getSuffix());
        return photoViewData;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(this.height);
        dest.writeString(this.prefix);
        dest.writeString(this.suffix);
        dest.writeValue(this.width);
    }

    public PhotoViewData() {
    }

    protected PhotoViewData(Parcel in) {
        this.height = (Long) in.readValue(Long.class.getClassLoader());
        this.prefix = in.readString();
        this.suffix = in.readString();
        this.width = (Long) in.readValue(Long.class.getClassLoader());
    }

    public static final Parcelable.Creator<PhotoViewData> CREATOR = new Parcelable.Creator<PhotoViewData>() {
        @Override
        public PhotoViewData createFromParcel(Parcel source) {
            return new PhotoViewData(source);
        }

        @Override
        public PhotoViewData[] newArray(int size) {
            return new PhotoViewData[size];
        }
    };
}
