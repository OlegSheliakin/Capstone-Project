package home.oleg.placesnearme.feature_map.di

import android.app.Activity
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.smedialink.feature_venue_detail.presentation.VenueViewModel
import dagger.Binds
import dagger.Module
import dagger.Provides
import home.oleg.placesnearme.feature_map.drawable_converter.DrawableConverter
import home.oleg.placesnearme.feature_map.drawable_converter.DrawableConverterImpl
import home.oleg.placesnearme.feature_map.presentation.sections.SectionProvider
import home.oleg.placesnearme.feature_map.presentation.ui.VenuesMapFragment
import home.oleg.placesnearme.feature_map.presentation.ui.adapter.CheckedItem
import home.oleg.placesnearme.feature_map.presentation.ui.adapter.SectionsAdapter
import home.oleg.placesnearme.feature_map.presentation.viewmodel.UserLocationViewModel
import home.oleg.placesnearme.feature_map.presentation.viewmodel.VenuesViewModel

@Module
abstract class VenuesMapFragmentModule {

    @Binds
    internal abstract fun provideConverter(impl: DrawableConverterImpl): DrawableConverter

    @Module
    companion object {

        @JvmStatic
        @Provides
        internal fun provideActivity(fragment: VenuesMapFragment): Activity {
            return fragment.activity!!
        }

        @JvmStatic
        @Provides
        internal fun provideVenueViewModel(
                fragment: VenuesMapFragment,
                factory: ViewModelProvider.Factory): VenueViewModel {
            return ViewModelProviders.of(fragment, factory).get(VenueViewModel::class.java)
        }

        @JvmStatic
        @Provides
        internal fun provideMapViewModel(
                fragment: VenuesMapFragment,
                factory: ViewModelProvider.Factory): VenuesViewModel {
            return ViewModelProviders.of(fragment, factory).get(VenuesViewModel::class.java)
        }

        @JvmStatic
        @Provides
        internal fun provideUserLocationViewModel(
                fragment: VenuesMapFragment,
                factory: ViewModelProvider.Factory): UserLocationViewModel {
            return ViewModelProviders.of(fragment, factory).get(UserLocationViewModel::class.java)
        }

        @JvmStatic
        @Provides
        internal fun provideLifeCycleOwner(mapFragment: VenuesMapFragment): LifecycleOwner = mapFragment.viewLifecycleOwner

        @JvmStatic
        @Provides
        internal fun provideSectionsAdapter(venuesMapFragment: VenuesMapFragment): SectionsAdapter {
            val sectionProvider = SectionProvider()
            val seCheckedItems = CheckedItem.wrap(sectionProvider.sections)
            return SectionsAdapter(seCheckedItems, venuesMapFragment)
        }
    }
}
