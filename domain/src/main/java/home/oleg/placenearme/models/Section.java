package home.oleg.placenearme.models;

import java.util.List;

import io.reactivex.annotations.NonNull;

public final class Section {

    private final Type sectionType;
    private final List<DetailedVenue> venues;

    public Section(@NonNull Type sectionType, @NonNull List<DetailedVenue> venues) {
        this.sectionType = sectionType;
        this.venues = venues;
    }

    public Type getSectionType() {
        return sectionType;
    }

    public List<DetailedVenue> getVenues() {
        return venues;
    }

    public enum Type {
        FOOD("food"),
        DRINKS("drinks"),
        TOP("topPicks"),
        COFFEE("coffee"),
        OUTDOORS("outdoors"),
        SHOPS("shops"),
        ARTS("arts"),
        SIGHTS("sights"),
        TRENDING("trending");

        private String value;

        Type(String value) {
            this.value = value;
        }

        public String getValue() {
            return value;
        }
    }
}
