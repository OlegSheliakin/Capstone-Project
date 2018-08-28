package home.oleg.placenearme.repositories;

public enum Section {
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

    Section(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}