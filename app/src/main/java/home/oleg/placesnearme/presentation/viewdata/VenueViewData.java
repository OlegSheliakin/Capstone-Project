package home.oleg.placesnearme.presentation.viewdata;

import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.Nullable;

import com.smedialink.common.Pair;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import home.oleg.placenearme.models.DetailedVenue;
import home.oleg.placenearme.models.Photo;
import home.oleg.placenearme.models.Section;

/**
 * Created by Oleg Sheliakin on 14.08.2018.
 * Contact me by email - olegsheliakin@gmail.com
 */
public class VenueViewData implements Parcelable {

    private String title;
    private String address;
    private double lat;
    private double lng;
    private List<String> photoUrls;
    private String description;
    @Nullable
    private Section sectionType;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
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

    public List<String> getPhotoUrls() {
        if(photoUrls == null) {
            return Collections.emptyList();
        } else {
            return new ArrayList<>(photoUrls);
        }
    }

    public void setPhotoUrls(List<String> photoUrls) {
        this.photoUrls = new ArrayList<>(photoUrls);
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Nullable
    public Section getSectionType() {
        return sectionType;
    }

    public void setSectionType(@Nullable Section sectionType) {
        this.sectionType = sectionType;
    }

    public static List<VenueViewData> mapFrom(Pair<Section, List<DetailedVenue>> pair) {
        if (pair.getSecond().isEmpty()) {
            return Collections.emptyList();
        }

        List<VenueViewData> list = new ArrayList<>();
        for (DetailedVenue venue : pair.getSecond()) {
            list.add(VenueViewData.mapFrom(venue, pair.getFirst()));
        }
        return list;
    }

    public static List<VenueViewData> mapFrom(Collection<DetailedVenue> venues) {
        if (venues.isEmpty()) {
            return Collections.emptyList();
        }

        List<VenueViewData> list = new ArrayList<>();
        for (DetailedVenue venue : venues) {
            list.add(VenueViewData.mapFrom(venue, null));
        }
        return list;
    }

    public static VenueViewData mapFrom(DetailedVenue venue, Section section) {
        VenueViewData venueViewObject = new VenueViewData();
        venueViewObject.setTitle(venue.getName());
        venueViewObject.setAddress(venue.getLocation().getAddress());
        venueViewObject.setLat(venue.getLocation().getLat());
        venueViewObject.setLng(venue.getLocation().getLng());
        venueViewObject.setSectionType(section);
        venueViewObject.setDescription(venue.getDescription());

        List<Photo> photos = venue.getPhotos();

        List<String> photoUrls = new ArrayList<>();
        for (Photo photo : photos) {
            photoUrls.add(photo.getImageUrl());
        }
        venueViewObject.setPhotoUrls(photoUrls);

        return venueViewObject;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.title);
        dest.writeString(this.address);
        dest.writeDouble(this.lat);
        dest.writeDouble(this.lng);
        dest.writeStringList(this.photoUrls);
        dest.writeString(this.description);
        dest.writeInt(this.sectionType == null ? -1 : this.sectionType.ordinal());
    }

    public VenueViewData() {
    }

    protected VenueViewData(Parcel in) {
        this.title = in.readString();
        this.address = in.readString();
        this.lat = in.readDouble();
        this.lng = in.readDouble();
        this.photoUrls = in.createStringArrayList();
        this.description = in.readString();
        int tmpSectionType = in.readInt();
        this.sectionType = tmpSectionType == -1 ? null : Section.values()[tmpSectionType];
    }

    public static final Parcelable.Creator<VenueViewData> CREATOR = new Parcelable.Creator<VenueViewData>() {
        @Override
        public VenueViewData createFromParcel(Parcel source) {
            return new VenueViewData(source);
        }

        @Override
        public VenueViewData[] newArray(int size) {
            return new VenueViewData[size];
        }
    };
}
