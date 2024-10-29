package tech.mujtaba.entain.feature.race.data.mapping

import java.time.LocalDateTime
import java.time.ZoneOffset
import java.time.ZonedDateTime
import tech.mujtaba.entain.feature.race.data.NextRacesResponse
import tech.mujtaba.entain.feature.race.data.RaceSummary as NetworkRaceSummary
import tech.mujtaba.entain.feature.race.domain.RaceCategory
import tech.mujtaba.entain.feature.race.domain.RaceSummary

internal fun NextRacesResponse.toDomain(): List<RaceSummary> = data.nextToGoIds.mapNotNull {
    data.raceSummaries[it]?.toDomain()
}

internal fun NetworkRaceSummary.toDomain(): RaceSummary = RaceSummary(
    id,
    name,
    number,
    meetingId,
    meetingName,
    LocalDateTime.ofEpochSecond(advertisedStart.seconds, 0, ZoneOffset.from(ZonedDateTime.now())),
    RaceCategory.entries.first { it.id == categoryId },
    venueName,
    venueState
)
