package home.oleg.placesnearme.data.mapper;

import java.util.ArrayList;
import java.util.List;

import home.oleg.placenearme.models.Photo;
import home.oleg.placesnearme.data.model.PhotoDbEntity;

/**
 * Created by Oleg Sheliakin on 25.09.2018.
 * Contact me by email - olegsheliakin@gmail.com
 */
public final class PhotoMapper {

    private PhotoMapper() {
    }

    public static Photo map(home.oleg.placesnearme.network.models.Photo photo) {
        Photo result = new Photo();

        result.setHeight(photo.getHeight());
        result.setPrefix(photo.getPrefix());
        result.setSuffix(photo.getSuffix());
        result.setWidth(photo.getWidth());
        return result;
    }

    public static List<Photo> map(List<home.oleg.placesnearme.network.models.Photo> photos) {
        List<Photo> result = new ArrayList<>();

        for (home.oleg.placesnearme.network.models.Photo photo : photos) {
            result.add(map(photo));
        }

        return result;
    }

    public static List<Photo> mapFromDb(List<PhotoDbEntity> photos) {
        List<Photo> result = new ArrayList<>();

        for (PhotoDbEntity photo : photos) {
            result.add(photo.getPhoto());
        }

        return result;
    }

    public static List<PhotoDbEntity> mapToDb(List<Photo> photos) {
        List<PhotoDbEntity> result = new ArrayList<>();

        for (Photo photo : photos) {
            PhotoDbEntity photoDbEntity = new PhotoDbEntity();
            photoDbEntity.setPhoto(photo);
            result.add(photoDbEntity);
        }

        return result;
    }
}
