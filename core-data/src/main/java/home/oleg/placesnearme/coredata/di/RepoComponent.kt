package home.oleg.placesnearme.coredata.di

import dagger.Component
import home.oleg.placesnearme.coredi.api.AppApi
import javax.inject.Singleton

@Singleton
@Component(
        dependencies = [AppApi::class],
        modules = [
            RepositoryModule::class,
            DbModule::class,
            NetworkModule::class])
interface RepoComponent : RepoApi