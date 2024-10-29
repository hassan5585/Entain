package tech.mujtaba.entain.feature.race.ui.upcoming

import java.time.LocalDateTime
import tech.mujtaba.entain.feature.race.domain.RaceCategory

internal data class RaceUiSummary(
    val id: String,
    val name: String,
    val number: Int,
    val venueName: String,
    val venueState: String,
    val startDateTime: String,
    val category: RaceCategory,
    val timeToStartInSeconds: Long,
    val advertisedStart: LocalDateTime,
    val meetingName: String
)
