package tech.mujtaba.entain.feature.race.data.respository

import javax.inject.Inject
import javax.inject.Singleton
import tech.mujtaba.entain.feature.race.data.mapping.toDomain
import tech.mujtaba.entain.feature.race.data.service.RaceService
import tech.mujtaba.entain.feature.race.domain.RaceSummary
import tech.mujtaba.entain.feature.race.domain.repository.RaceRepository

@Singleton
internal class RaceRepositoryImpl @Inject constructor(
    private val raceService: RaceService
) : RaceRepository {
    override suspend fun nextRaces(count: Int): Result<List<RaceSummary>> {
        return try {
            Result.success(raceService.getNextRaces(count).toDomain())
        } catch (throwable: Throwable) {
            Result.failure(throwable)
        }
    }
}
