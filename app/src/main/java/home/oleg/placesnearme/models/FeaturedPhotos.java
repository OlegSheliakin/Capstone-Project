package home.oleg.placesnearme.models;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Oleg on 30.05.2016.
 */
public class FeaturedPhotos {

    private Integer count;
    private List<ItemsPhoto> items = new ArrayList<>();

    public Integer getCount() {
        return count;
    }

    public List<ItemsPhoto> getItems() {
        return items;
    }

    public class ItemsPhoto {

        private String id;
        private Integer createdAt;
        private String prefix;
        private String suffix;
        private Integer height;
        private Integer width;

        public String getId() {
            return id;
        }

        public Integer getCreatedAt() {
            return createdAt;
        }

        public String getPrefix() {
            return prefix;
        }

        public String getSuffix() {
            return suffix;
        }

        public Integer getHeight() {
            return height;
        }

        public Integer getWidth() {
            return width;
        }

        public String getPhotoURL (){
         return getPrefix()+getHeight()+"x"+getWidth()+getSuffix();
        }
    }
}
