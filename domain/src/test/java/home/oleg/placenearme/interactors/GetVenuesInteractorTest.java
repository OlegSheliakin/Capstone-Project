package home.oleg.placenearme.interactors;

import com.smedialink.common.Pair;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import home.oleg.placenearme.models.DetailedVenue;
import home.oleg.placenearme.models.Section;
import home.oleg.placenearme.models.UserLocation;
import home.oleg.placenearme.models.Venue;
import home.oleg.placenearme.repositories.DetailedVenueRepository;
import home.oleg.placenearme.repositories.UserLocationRepository;
import home.oleg.placenearme.repositories.VenueRepository;
import home.oleg.placenearme.repositories.VenueRequestParams;
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
    GetRecomendedVenuesInteractor subject;

    private UserLocation fakeUserLocation = new UserLocation(45.0, 45.0);

    private VenueRequestParams filter;

    private List<Venue> fakeVenues = Arrays.asList(
            new Venue() {{
                setId("0");
            }}
    );

    @Before
    public void setUp() {
        when(userLocationRepository.getLocation()).thenReturn(Single.just(fakeUserLocation));

        filter = new VenueRequestParams(1000, fakeUserLocation.getLat(), fakeUserLocation.getLng());
    }

    @Test
    public void shouldReturnEmptyList() {
        when(venueRepository.getRecommendedBySection(any(), any()))
                .thenReturn(Single.just(Collections.emptyList()));

        Pair<Section.Type, List<DetailedVenue>> pair = subject.getRecommendedSection(Section.Type.ARTS).blockingGet();

        verify(venueRepository, times(1)).getRecommendedBySection(Section.Type.ARTS, filter);
        verifyNoMoreInteractions(detailedVenueRepository);

        assertTrue(pair.getSecond().isEmpty());
    }

    @Test
    public void shouldReturnListDetailedVenue() {
        when(venueRepository.getRecommendedBySection(any(), any()))
                .thenReturn(Single.just(fakeVenues));
        when(detailedVenueRepository.getDetailedVenueById(anyString())).thenReturn(Single.just(new DetailedVenue()));

        Pair<Section.Type, List<DetailedVenue>> pair = subject.getRecommendedSection(Section.Type.ARTS).blockingGet();

        verify(detailedVenueRepository, times(fakeVenues.size())).getDetailedVenueById(any());

        assertFalse(pair.getSecond().isEmpty());
    }

}