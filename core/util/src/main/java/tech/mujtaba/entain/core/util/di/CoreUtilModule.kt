package tech.mujtaba.entain.core.util.di

import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import tech.mujtaba.entain.core.util.DateTimeHelper
import tech.mujtaba.entain.core.util.DateTimeHelperImpl

@Module
@InstallIn(SingletonComponent::class)
internal interface CoreUtilModule {
    @Binds
    fun bindDateTimeProvider(dateTimeProvider: DateTimeHelperImpl): DateTimeHelper

    companion object {
        @Provides
        @ComputationDispatcher
        fun provideComputationDispatcher(): CoroutineDispatcher = Dispatchers.Default
    }
}
