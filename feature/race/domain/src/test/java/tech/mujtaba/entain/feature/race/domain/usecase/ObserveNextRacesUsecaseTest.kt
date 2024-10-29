package tech.mujtaba.entain.feature.race.domain.usecase

import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.mockk
import java.time.LocalDateTime
import java.time.Month
import kotlin.time.Duration.Companion.seconds
import kotlin.time.measureTime
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.take
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import tech.mujtaba.entain.core.util.DateTimeHelper
import tech.mujtaba.entain.feature.race.domain.RaceSummary
import tech.mujtaba.entain.feature.race.domain.repository.RaceRepository
import tech.mujtaba.entain.feature.race.domain.usecase.ObserveNextRacesUsecase.Companion.COUNT_NEXT_RACES
import tech.mujtaba.entain.feature.race.domain.usecase.ObserveNextRacesUsecase.Companion.MAX_RACES
import tech.mujtaba.entain.feature.race.domain.usecase.ObserveNextRacesUsecase.Companion.REFRESH_INTERVAL_IN_SECONDS
import tech.mujtaba.entain.feature.race.domain.usecase.ObserveNextRacesUsecase.Companion.REMOVE_RACE_INTERVAL_IN_SECONDS

class ObserveNextRacesUsecaseTest {

    @MockK
    lateinit var raceRepository: RaceRepository

    @MockK
    lateinit var dateTimeProvider: DateTimeHelper

    @BeforeEach
    fun setup() {
        MockKAnnotations.init(this, relaxUnitFun = true)
    }

    @Test
    fun `given usecase is invoked assert we are fetching fresh results every 1 minute`() = runTest {
        // setup
        every { dateTimeProvider.currentDateTime() } returns LocalDateTime.now()
        coEvery { raceRepository.nextRaces(any()) } returns Result.success(emptyList())

        // Act
        val duration = testScheduler.timeSource.measureTime {
            // To get to the first delay we need to take 2 items from the flow
            // The first emission happens before the delay is called
            getUsecase().invoke().take(2).collect()
        }
        testScheduler.runCurrent()
        testScheduler.advanceTimeBy(REFRESH_INTERVAL_IN_SECONDS.seconds)

        // Assert
        assertEquals(REFRESH_INTERVAL_IN_SECONDS * 1000, duration.inWholeMilliseconds)
    }

    @Test
    fun `given the api returns results filter out past advertised results that started more than a minute ago`() = runTest {
        val testTime = LocalDateTime.of(2024, Month.OCTOBER, 2, 12, 12)
        // setup
        every { dateTimeProvider.currentDateTime() } returns testTime
        val resultOne = mockk<RaceSummary>(relaxed = true)
        every { resultOne.advertisedStart } returns testTime.plusHours(1)
        val resultTwo = mockk<RaceSummary>(relaxed = true)
        every { resultTwo.advertisedStart } returns testTime.minusHours(1)
        val resultThree = mockk<RaceSummary>(relaxed = true)
        every { resultThree.advertisedStart } returns testTime.minusSeconds(
            REMOVE_RACE_INTERVAL_IN_SECONDS - 1
        )
        val mockResults = listOf(
            resultOne,
            resultTwo,
            resultThree
        )
        coEvery { raceRepository.nextRaces(any()) } returns Result.success(mockResults)

        // Act
        getUsecase().invoke().take(1).collectLatest {
            // Assert
            assertTrue(it.isSuccess)
            assertEquals(2, it.getOrThrow().size)
        }
    }

    @Test
    fun `given the api returns results sort results by advertised start time`() = runTest {
        val testTime = LocalDateTime.of(2024, Month.OCTOBER, 2, 12, 12)
        // setup
        every { dateTimeProvider.currentDateTime() } returns testTime
        val resultOne = mockk<RaceSummary>(relaxed = true)
        every { resultOne.advertisedStart } returns testTime.plusHours(2)
        val resultTwo = mockk<RaceSummary>(relaxed = true)
        every { resultTwo.advertisedStart } returns testTime.plusHours(1)
        val resultThree = mockk<RaceSummary>(relaxed = true)
        every { resultThree.advertisedStart } returns testTime.plusHours(3)
        val mockResults = listOf(
            resultOne,
            resultTwo,
            resultThree
        )
        coEvery { raceRepository.nextRaces(any()) } returns Result.success(mockResults)

        // Act
        getUsecase().invoke().take(1).collectLatest {
            // Assert
            assertTrue(it.isSuccess)
            val result = it.getOrThrow()
            assertEquals(resultTwo, result[0])
            assertEquals(resultOne, result[1])
            assertEquals(resultThree, result[2])
        }
    }

    @Test
    fun `given the api returns results take only max five items`() = runTest {
        val testTime = LocalDateTime.of(2024, Month.OCTOBER, 2, 12, 12)
        // setup
        val mockResults = mutableListOf<RaceSummary>().apply {
            repeat(COUNT_NEXT_RACES) { index ->
                val result = mockk<RaceSummary>(relaxed = true)
                every { result.advertisedStart } returns testTime.plusHours(index.toLong())
                add(result)
            }
        }
        every { dateTimeProvider.currentDateTime() } returns testTime
        coEvery { raceRepository.nextRaces(any()) } returns Result.success(mockResults)

        // Act
        getUsecase().invoke().take(1).collectLatest {
            // Assert
            assertTrue(it.isSuccess)
            val result = it.getOrThrow()
            assertEquals(MAX_RACES, result.size)
        }
    }

    private fun getUsecase() = ObserveNextRacesUsecase(raceRepository, dateTimeProvider)
}
