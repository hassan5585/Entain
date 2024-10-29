package tech.mujtaba.entain.core.util

import java.time.LocalDateTime
import java.time.temporal.ChronoUnit
import javax.inject.Inject

internal class DateTimeHelperImpl @Inject constructor() : DateTimeHelper {
    override fun currentDateTime(): LocalDateTime = LocalDateTime.now()

    override fun timeDifferenceInSeconds(advertisedTime: LocalDateTime): Long {
        val differenceInTime = ChronoUnit.SECONDS.between(currentDateTime(), advertisedTime)
        return if (differenceInTime > 0) differenceInTime else 0
    }
}
