package home.oleg.placenearme.interactors;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Collections;
import java.util.List;

import home.oleg.placenearme.models.DetailedVenue;
import home.oleg.placenearme.models.UserLocation;
import home.oleg.placenearme.repositories.Category;
import home.oleg.placenearme.repositories.DetailedVenueRepository;
import home.oleg.placenearme.repositories.UserLocationRepository;
import home.oleg.placenearme.repositories.VenueRepository;
import io.reactivex.Single;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class GetVenuesInteractorTest {

    @Mock
    VenueRepository venueRepository;

    @Mock
    DetailedVenueRepository detailedVenueRepository;

    @Mock
    UserLocationRepository userLocationRepository;

    @InjectMocks
    GetVenuesInteractor subject;

    private UserLocation fakeUserLocation = new UserLocation(0.0, 0.0);

    @Before
    public void setUp() {
        when(userLocationRepository.getLocation()).thenReturn(Single.just(fakeUserLocation));
    }

    @Test
    public void getRecommendedVenue() {
        VenueRepository.Filter filter = new VenueRepository.Filter();
        filter.setLat(fakeUserLocation.getLat());
        filter.setLng(fakeUserLocation.getLng());

        when(venueRepository.getRecommendedByCategory(any(), any()))
                .thenReturn(Single.just(Collections.emptyList()));

        List<DetailedVenue> list = subject.getRecommendedVenue(Category.ARTS).blockingGet();

        verify(venueRepository, times(1)).getRecommendedByCategory(Category.ARTS, filter);
        verifyNoMoreInteractions(detailedVenueRepository);

        assertTrue(list.isEmpty());
    }

    @Test
    public void searchVenue() {
    }
}