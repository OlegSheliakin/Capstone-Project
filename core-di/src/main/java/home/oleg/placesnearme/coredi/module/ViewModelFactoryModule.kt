package home.oleg.placesnearme.coredi.module

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import dagger.Module
import dagger.Provides
import javax.inject.Provider

@Module
open class ViewModelFactoryModule {

    @Provides
    fun provide(map: MutableMap<Class<out ViewModel>, Provider<ViewModel>>): ViewModelProvider.Factory {
        return DaggerViewModelFactory(map)
    }

}

class DaggerViewModelFactory(private val map: MutableMap<Class<out ViewModel>, Provider<ViewModel>>) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        val viewModelProvider = map[modelClass]
                ?: throw IllegalArgumentException("model class provider"
                        + modelClass
                        + " not found")

        return viewModelProvider.get() as T
    }

}