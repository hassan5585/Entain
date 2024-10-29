package tech.mujtaba.entain.feature.race.domain.repository

import tech.mujtaba.entain.feature.race.domain.RaceSummary

interface RaceRepository {
    /** Fetches the next [count] races
     * @return [Result.success] if api call succeeds
     * @return [Result.failure] in case an exception is thrown
     */
    suspend fun nextRaces(count: Int): Result<List<RaceSummary>>
}
