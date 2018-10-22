package home.oleg.feature_favorite_venues;

public class EmptyViewItem implements ItemViewType {

    public static int VIEW_TYPE = 0;

    @Override
    public int getViewType() {
        return VIEW_TYPE;
    }
}
