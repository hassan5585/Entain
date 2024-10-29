package tech.mujtaba.entain.feature.race.ui.upcoming

import java.time.format.DateTimeFormatter
import javax.inject.Inject
import tech.mujtaba.entain.core.util.DateTimeHelper
import tech.mujtaba.entain.feature.race.domain.RaceSummary

internal class SummaryMapper @Inject constructor(
    private val dateTimeHelper: DateTimeHelper,
    private val formatter: DateTimeFormatter
) {

    fun map(summary: RaceSummary): RaceUiSummary {
        return with(summary) {
            RaceUiSummary(
                id = id,
                name = name,
                venueName = venueName,
                venueState = venueState,
                startDateTime = formatter.format(advertisedStart),
                category = category,
                timeToStartInSeconds = dateTimeHelper.timeDifferenceInSeconds(advertisedStart),
                advertisedStart = advertisedStart,
                meetingName = meetingName,
                number = number
            )
        }
    }
}
