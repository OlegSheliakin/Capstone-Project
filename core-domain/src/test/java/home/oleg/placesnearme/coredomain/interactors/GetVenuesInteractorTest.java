package home.oleg.placesnearme.coredomain.interactors;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import home.oleg.placesnearme.coredomain.models.Category;
import home.oleg.placesnearme.coredomain.models.LatLng;
import home.oleg.placesnearme.coredomain.models.UserLocation;
import home.oleg.placesnearme.coredomain.models.Section;
import home.oleg.placesnearme.coredomain.models.Venue;
import home.oleg.placesnearme.coredomain.repositories.SectionRepository;
import home.oleg.placesnearme.coredomain.repositories.UserLatLngRepository;
import home.oleg.placesnearme.coredomain.repositories.VenueRepository;
import home.oleg.placesnearme.coredomain.repositories.VenueRequestParams;
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
    UserLatLngRepository userLocationRepository;

    @Mock
    SectionRepository sectionRepository;

    @InjectMocks
    GetRecommendedVenues subject;

    private LatLng fakeUserLatLng = new LatLng(45.0, 45.0);

    private VenueRequestParams filter;

    private List<Venue> fakeVenues = Arrays.asList(
            new Venue(
                    new Category("", "", ""),
                    "0",
                    new UserLocation("", 0,0),"",0));

    @Before
    public void setUp() {
        when(userLocationRepository.getLatlng()).thenReturn(Single.just(fakeUserLatLng));

        filter = new VenueRequestParams(1000, fakeUserLatLng.getLat(), fakeUserLatLng.getLng());
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