package home.oleg.placesnearme.presentation.feature.map.marker;

import org.junit.Rule;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;

import home.oleg.placesnearme.common.converter.DrawableConverter;
import com.home.olegsheliakin.corettools.resource.provider.ResourceProvider;

/**
 * Created by Oleg Sheliakin on 28.08.2018.
 * Contact me by email - olegsheliakin@gmail.com
 */
public class MarkerIconProviderTest {

    @Rule
    public MockitoRule mockitoRule = MockitoJUnit.rule();

    @Mock
    ResourceProvider resourceProvider;

    @Mock
    DrawableConverter drawableConverter;

   /* @InjectMocks
    MarkerIconProvider subject;

    @Test
    public void testSetColorAndConvertDrawable() {
        GradientDrawable drawable = mock(GradientDrawable.class);
        when(resourceProvider.getDrawable(anyInt())).thenReturn(drawable);
        when(resourceProvider.getColor(R.color.colorCategoryTopPicks)).thenReturn(0);

        subject.getIconByCategory(Section.TOP);

        verify(drawable).setColor(0);
        verify(drawableConverter).convert(drawable);
    }

    @Test(expected = IllegalStateException.class)
    public void throwExceptionWhenNotGradientDrawable() {
        Drawable drawable = mock(ShapeDrawable.class);
        when(resourceProvider.getDrawable(anyInt())).thenReturn(drawable);
        when(resourceProvider.getColor(R.color.colorCategoryTopPicks)).thenReturn(0);

        subject.getIconByCategory(Section.TOP);
    }*/
}