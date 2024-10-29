package tech.mujtaba.entain.feature.race.ui.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import java.time.format.DateTimeFormatter
import java.time.format.FormatStyle

@Module
@InstallIn(SingletonComponent::class)
internal class RaceUiModule {
    @Provides
    fun provideDateTimeFormatter(): DateTimeFormatter = DateTimeFormatter.ofLocalizedDateTime(FormatStyle.MEDIUM)
}
