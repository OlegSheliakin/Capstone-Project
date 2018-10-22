package home.oleg.placesnearme.core_presentation.viewdata;

import android.os.Parcel;
import android.os.Parcelable;

import home.oleg.placenearme.models.Icon;

/**
 * Created by Oleg Sheliakin on 21.09.2018.
 * Contact me by email - olegsheliakin@gmail.com
 */
public class IconViewData implements Parcelable {

    public static final int SIZE = 64;
    private String prefix;
    private String suffix;

    public void setPrefix(String prefix) {
        this.prefix = prefix;
    }

    public void setSuffix(String suffix) {
        this.suffix = suffix;
    }


    public String getPrefix() {
        return prefix;
    }

    public String getSuffix() {
        return suffix;
    }

    public String getIconUrlWhite() {
        return prefix + SIZE + suffix;
    }

    public String getIconUrlGray() {
        return prefix + "bg_" + SIZE + suffix;
    }

    public static IconViewData map(Icon icon) {
        IconViewData iconViewData = new IconViewData();
        iconViewData.setSuffix(icon.getSuffix());
        iconViewData.setPrefix(icon.getPrefix());
        return iconViewData;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.prefix);
        dest.writeString(this.suffix);
    }

    public IconViewData() {
    }

    protected IconViewData(Parcel in) {
        this.prefix = in.readString();
        this.suffix = in.readString();
    }

    public static final Creator<IconViewData> CREATOR = new Creator<IconViewData>() {
        @Override
        public IconViewData createFromParcel(Parcel source) {
            return new IconViewData(source);
        }

        @Override
        public IconViewData[] newArray(int size) {
            return new IconViewData[size];
        }
    };
}
