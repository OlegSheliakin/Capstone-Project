package home.oleg.placesnearme.mapper;

import java.util.ArrayList;
import java.util.List;

import home.oleg.placenearme.models.Photo;

/**
 * Created by Oleg Sheliakin on 25.09.2018.
 * Contact me by email - olegsheliakin@gmail.com
 */
public final class PhotoMapper {

    private PhotoMapper() {
    }

    public static Photo map(home.oleg.placesnearme.core_network.models.Photo photo) {
        Photo result = new Photo();

        result.setCreatedAt(photo.getCreatedAt());
        result.setHeight(photo.getHeight());
        result.setId(photo.getId());
        result.setPrefix(photo.getPrefix());
        result.setSuffix(photo.getSuffix());
        result.setWidth(photo.getWidth());
        result.setVisibility(photo.getVisibility());
        return result;
    }

    public static List<Photo> map(List<home.oleg.placesnearme.core_network.models.Photo> photos) {
        List<Photo> result = new ArrayList<>();

        for (home.oleg.placesnearme.core_network.models.Photo photo : photos) {
            result.add(map(photo));
        }

        return result;
    }
}
