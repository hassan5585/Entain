package tech.mujtaba.entain.feature.race.ui.upcoming

import io.mockk.MockKAnnotations
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.mockk
import io.mockk.verify
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.emptyFlow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertInstanceOf
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import tech.mujtaba.entain.core.util.DateTimeHelper
import tech.mujtaba.entain.feature.race.domain.usecase.ObserveNextRacesUsecase
import tech.mujtaba.entain.feature.race.ui.upcoming.RaceViewmodel.State

@OptIn(ExperimentalCoroutinesApi::class)
internal class RaceViewmodelTest {

    @MockK
    lateinit var observeNextRacesUsecase: ObserveNextRacesUsecase

    @MockK
    lateinit var summaryMapper: SummaryMapper

    @MockK
    lateinit var dateTimeHelper: DateTimeHelper

    private lateinit var viewmodel: RaceViewmodel

    @BeforeEach
    fun setup() {
        MockKAnnotations.init(this, relaxUnitFun = true)
        val dispatcher = UnconfinedTestDispatcher()
        Dispatchers.setMain(dispatcher)
        viewmodel = RaceViewmodel(observeNextRacesUsecase, summaryMapper, dateTimeHelper, dispatcher)
    }

    @AfterEach
    fun teardown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `given vm is init state should be loading`() = runTest {
        with(viewmodel) {
            assertEquals(State.Loading, state.value)
        }
    }

    @Test
    fun `given start fetching races is called invoke usecase`() = runTest {
        every { observeNextRacesUsecase.invoke() } returns emptyFlow()
        viewmodel.startFetchingRaces()

        verify(atMost = 1) { observeNextRacesUsecase.invoke() }
    }

    @Test
    fun `given error result returned from usecase state should be error`() = runTest {
        every { observeNextRacesUsecase.invoke() } returns flowOf(Result.failure(Throwable("")))

        with(viewmodel) {
            // Act
            startFetchingRaces()

            // Assert
            assertEquals(State.Error, state.value)
        }
    }

    @Test
    fun `given success result returned from usecase state should be Success`() = runTest {
        every { observeNextRacesUsecase.invoke() } returns flowOf(
            Result.success(
                listOf(
                    mockk(relaxed = true)
                )
            )
        )

        every { summaryMapper.map(any()) } returns mockk(relaxed = true)

        with(viewmodel) {
            // Act
            startFetchingRaces()

            advanceUntilIdle()

            // Assert
            assertInstanceOf(State.Success::class.java, state.value)
        }
    }
}
