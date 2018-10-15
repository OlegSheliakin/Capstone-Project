package home.oleg.placesnearme.core_presentation.viewdata;

import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.Nullable;

import home.oleg.placenearme.models.Section;

public class ShortVenueViewData implements Parcelable {

    private String id;
    private String title;
    private Double distance;
    private String address;
    private double lat;
    private double lng;
    private IconViewData iconViewData;
    private String categoryName;

    @Nullable
    private Section sectionType;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public String getTitle() {
        return title;
    }

    public IconViewData getIconViewData() {
        return iconViewData;
    }

    public void setIconViewData(IconViewData iconViewData) {
        this.iconViewData = iconViewData;
    }

    public String getCategoryIconUrl() {
        return iconViewData.getIconUrlGray();
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Double getDistance() {
        return distance;
    }

    public void setDistance(Double distance) {
        this.distance = distance;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public double getLng() {
        return lng;
    }

    public void setLng(double lng) {
        this.lng = lng;
    }

    @Nullable
    public Section getSectionType() {
        return sectionType;
    }

    public void setSectionType(@Nullable Section sectionType) {
        this.sectionType = sectionType;
    }

    public ShortVenueViewData() {
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.id);
        dest.writeString(this.title);
        dest.writeDouble(this.distance);
        dest.writeString(this.address);
        dest.writeDouble(this.lat);
        dest.writeDouble(this.lng);
        dest.writeParcelable(this.iconViewData, flags);
        dest.writeString(this.categoryName);
        dest.writeInt(this.sectionType == null ? -1 : this.sectionType.ordinal());
    }

    protected ShortVenueViewData(Parcel in) {
        this.id = in.readString();
        this.title = in.readString();
        this.distance = in.readDouble();
        this.address = in.readString();
        this.lat = in.readDouble();
        this.lng = in.readDouble();
        this.iconViewData = in.readParcelable(IconViewData.class.getClassLoader());
        this.categoryName = in.readString();
        int tmpSectionType = in.readInt();
        this.sectionType = tmpSectionType == -1 ? null : Section.values()[tmpSectionType];
    }

    public static final Parcelable.Creator<ShortVenueViewData> CREATOR = new Parcelable.Creator<ShortVenueViewData>() {
        @Override
        public ShortVenueViewData createFromParcel(Parcel source) {
            return new ShortVenueViewData(source);
        }

        @Override
        public ShortVenueViewData[] newArray(int size) {
            return new ShortVenueViewData[size];
        }
    };
}
