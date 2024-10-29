package tech.mujtaba.entain.core.util

import java.time.LocalDateTime

interface DateTimeHelper {
    /**
     * @return the current datetime [LocalDateTime.now]
     */
    fun currentDateTime(): LocalDateTime

    /**
     * @return Difference in seconds between advertised time and current time.
     *
     * If the advertised time is after the current time, this returns 0
     */
    fun timeDifferenceInSeconds(advertisedTime: LocalDateTime): Long
}
