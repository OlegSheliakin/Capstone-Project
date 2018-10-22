package home.oleg.feature_favorite_venues.state;

public interface State<VIEW> {
    void apply(VIEW view);
}
