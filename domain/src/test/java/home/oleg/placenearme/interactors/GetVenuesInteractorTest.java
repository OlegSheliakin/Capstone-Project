package home.oleg.placenearme.interactors;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import home.oleg.placenearme.models.Category;
import home.oleg.placenearme.models.Location;
import home.oleg.placenearme.models.Section;
import home.oleg.placenearme.models.UserLocation;
import home.oleg.placenearme.models.Venue;
import home.oleg.placenearme.repositories.SectionRepository;
import home.oleg.placenearme.repositories.UserLocationRepository;
import home.oleg.placenearme.repositories.VenueRepository;
import home.oleg.placenearme.repositories.VenueRequestParams;
import io.reactivex.Single;
import kotlin.Pair;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class GetVenuesInteractorTest {

    @Mock
    VenueRepository venueRepository;

    @Mock
    UserLocationRepository userLocationRepository;

    @Mock
    SectionRepository sectionRepository;

    @InjectMocks
    GetRecommendedVenues subject;

    private UserLocation fakeUserLocation = new UserLocation(45.0, 45.0);

    private VenueRequestParams filter;

    private List<Venue> fakeVenues = Arrays.asList(
            new Venue(
                    new Category("", "", ""),
                    "0",
                    new Location("", 0,0),"",0));

    @Before
    public void setUp() {
        when(userLocationRepository.getLocation()).thenReturn(Single.just(fakeUserLocation));

        filter = new VenueRequestParams(1000, fakeUserLocation.getLat(), fakeUserLocation.getLng());
    }

    @Test
    public void shouldReturnEmptyList() {
        when(venueRepository.getRecommendedBySection(any(), any()))
                .thenReturn(Single.just(Collections.emptyList()));

        Pair<Section, List<Venue>> pair = subject.getRecommendedSection(Section.ARTS).blockingGet();

        assertTrue(pair.getSecond().isEmpty());
    }

    @Test
    public void shouldReturnListDetailedVenue() {
        when(venueRepository.getRecommendedBySection(any(), any()))
                .thenReturn(Single.just(fakeVenues));

        Pair<Section, List<Venue>> pair = subject.getRecommendedSection(Section.ARTS).blockingGet();

        assertFalse(pair.getSecond().isEmpty());
    }

}