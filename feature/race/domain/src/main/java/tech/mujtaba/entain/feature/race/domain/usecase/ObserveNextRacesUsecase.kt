package tech.mujtaba.entain.feature.race.domain.usecase

import javax.inject.Inject
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import tech.mujtaba.entain.core.util.DateTimeHelper
import tech.mujtaba.entain.feature.race.domain.RaceSummary
import tech.mujtaba.entain.feature.race.domain.repository.RaceRepository

/**
 * This usecase fetches returns the next [MAX_RACES] sorted by [RaceSummary.advertisedStart]
 *
 * The flow returned, emits every [REFRESH_INTERVAL_IN_SECONDS] seconds
 */
class ObserveNextRacesUsecase @Inject constructor(
    private val raceRepository: RaceRepository,
    private val dateTimeHelper: DateTimeHelper
) {
    companion object {
        const val MAX_RACES = 5
        const val REFRESH_INTERVAL_IN_SECONDS: Long = 60
        const val REMOVE_RACE_INTERVAL_IN_SECONDS: Long = 60
        const val COUNT_NEXT_RACES = 10
    }

    operator fun invoke(): Flow<Result<List<RaceSummary>>> {
        return refreshFlow().map {
            val currentTime = dateTimeHelper.currentDateTime()
            raceRepository
                .nextRaces(COUNT_NEXT_RACES)
                .map {
                    it.filter {
                        it.advertisedStart.isAfter(
                            currentTime.minusSeconds(
                                REMOVE_RACE_INTERVAL_IN_SECONDS
                            )
                        )
                    }.sortedBy { it.advertisedStart }.take(MAX_RACES)
                }
        }
    }

    /**
     * Signals a refresh internally
     */
    private fun refreshFlow(): Flow<Unit> {
        return flow {
            while (true) {
                emit(Unit)
                delay(REFRESH_INTERVAL_IN_SECONDS * 1000)
            }
        }
    }
}
