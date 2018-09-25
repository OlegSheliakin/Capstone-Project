package home.oleg.placesnearme.repositories;

import home.oleg.placesnearme.core_network.models.reposnses.Response;
import home.oleg.placesnearme.core_network.models.reposnses.VenueDetailResponse;

import home.oleg.placenearme.models.DetailedVenue;
import home.oleg.placenearme.repositories.DetailedVenueRepository;
import home.oleg.placesnearme.mapper.DetailedVenueMapper;
import home.oleg.placesnearme.network.service.IFourSquareAPI;
import io.reactivex.Single;

public class DetailedVenueRepositoryImpl implements DetailedVenueRepository{

    private final IFourSquareAPI api;

    public DetailedVenueRepositoryImpl(IFourSquareAPI api) {
        this.api = api;
    }

    @Override
    public Single<DetailedVenue> getDetailedVenueById(String venueId) {
        return api.getDetail(venueId)
                .map(Response::getResponse)
                .map(VenueDetailResponse::getVenue)
                .map(DetailedVenueMapper::map);
    }
}
