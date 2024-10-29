package tech.mujtaba.entain.feature.race.ui.di

import androidx.navigation.NavGraphBuilder
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import dagger.multibindings.IntoSet
import tech.mujtaba.entain.core.navigation.NavGraphProvider
import tech.mujtaba.entain.feature.race.ui.graph.raceNavGraph

@Module
@InstallIn(ActivityComponent::class)
internal class RaceUiActivityModule {
    @Provides
    @IntoSet
    fun provideRaceGraphProvider(): NavGraphProvider = object : NavGraphProvider {
        override val graph: NavGraphBuilder.() -> Unit = NavGraphBuilder::raceNavGraph
    }
}
