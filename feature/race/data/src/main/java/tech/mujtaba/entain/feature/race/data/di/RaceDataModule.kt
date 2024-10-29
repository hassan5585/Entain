package tech.mujtaba.entain.feature.race.data.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import tech.mujtaba.entain.feature.race.data.respository.RaceRepositoryImpl
import tech.mujtaba.entain.feature.race.domain.repository.RaceRepository

@Module
@InstallIn(SingletonComponent::class)
internal interface RaceDataModule {

    @Binds
    fun bindRaceRepository(impl: RaceRepositoryImpl): RaceRepository
}
