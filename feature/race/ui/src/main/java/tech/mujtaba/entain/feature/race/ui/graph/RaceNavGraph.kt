package tech.mujtaba.entain.feature.race.ui.graph

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import tech.mujtaba.entain.feature.race.navigation.UpComingRacesDestination
import tech.mujtaba.entain.feature.race.ui.upcoming.RaceScreen

internal fun NavGraphBuilder.raceNavGraph() {
    composable(route = UpComingRacesDestination.route, content = { entry ->
        RaceScreen(entry)
    })
}
