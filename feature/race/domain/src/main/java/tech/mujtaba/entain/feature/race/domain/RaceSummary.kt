package tech.mujtaba.entain.feature.race.domain

import java.time.LocalDateTime

data class RaceSummary(
    val id: String,
    val name: String,
    val number: Int,
    val meetingId: String,
    val meetingName: String,
    val advertisedStart: LocalDateTime,
    val category: RaceCategory,
    val venueName: String,
    val venueState: String
)
