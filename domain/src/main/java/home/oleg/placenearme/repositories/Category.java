package home.oleg.placenearme.repositories;

public enum Category {
    FOOD("food"),
    DRINKS("drinks"),
    TOP("topPicks"),
    COFFEE("coffee"),
    OUTDOORS("outdoors"),
    SHOPS("shops"),
    ARTS("arts"),
    SIGHTS("sights"),
    TRENDING("trending"),
    NEXTVENUES("nextVenues");

    private String value;

    Category(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}