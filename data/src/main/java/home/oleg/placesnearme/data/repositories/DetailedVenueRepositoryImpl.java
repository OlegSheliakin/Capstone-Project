package home.oleg.placesnearme.data.repositories;

import java.util.concurrent.TimeUnit;

import home.oleg.placenearme.models.DetailedVenue;
import home.oleg.placenearme.repositories.DetailedVenueRepository;
import home.oleg.placesnearme.data.dao.DetailedVenueWithPhotosDao;
import home.oleg.placesnearme.data.mapper.DetailedVenueMapper;
import home.oleg.placesnearme.data.model.DetailedVenueDbEntity;
import home.oleg.placesnearme.data.model.DetailedVenueWithPhotos;
import home.oleg.placesnearme.network.models.reposnses.Response;
import home.oleg.placesnearme.network.models.reposnses.VenueDetailResponse;
import home.oleg.placesnearme.network.service.IFourSquareAPI;
import io.reactivex.Flowable;
import io.reactivex.Single;

public class DetailedVenueRepositoryImpl implements DetailedVenueRepository {

    private final IFourSquareAPI api;
    private final DetailedVenueWithPhotosDao dao;

    public DetailedVenueRepositoryImpl(IFourSquareAPI api, DetailedVenueWithPhotosDao dao) {
        this.api = api;
        this.dao = dao;
    }

    @Override
    public Flowable<DetailedVenue> getDetailedVenueById(String venueId) {
        return Flowable.merge(
                dao.observeVenue(venueId).map(DetailedVenueMapper::map),
                getFromNetwork(venueId).toFlowable()
        ).debounce(300, TimeUnit.MILLISECONDS);
    }

    private Single<DetailedVenue> getFromNetwork(String venueId) {
        return api.getDetail(venueId)
                .map(Response::getResponse)
                .map(VenueDetailResponse::getVenue)
                .map(DetailedVenueMapper::map)
                .flatMap(detailedVenue -> {
                    DetailedVenueDbEntity detailedVenueDbEntity = dao.getDetailedVenueById(detailedVenue.getId());
                    if (detailedVenueDbEntity != null) {
                        detailedVenue.setFavorite(detailedVenueDbEntity.isFavorite());

                        DetailedVenueWithPhotos detailedVenueWithPhotos = DetailedVenueMapper.map(detailedVenue);
                        dao.insert(detailedVenueWithPhotos);
                        return Single.never();
                    }

                    return Single.just(detailedVenue);
                });
    }

}