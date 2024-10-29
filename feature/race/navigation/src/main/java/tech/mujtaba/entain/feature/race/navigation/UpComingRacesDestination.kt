package tech.mujtaba.entain.feature.race.navigation

import tech.mujtaba.entain.core.navigation.Destination

data object UpComingRacesDestination : Destination {
    override val route: String
        get() = "UpcomingRaces"
}
