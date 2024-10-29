package tech.mujtaba.entain.feature.race.ui.upcoming

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import kotlinx.coroutines.yield
import tech.mujtaba.entain.core.util.DateTimeHelper
import tech.mujtaba.entain.core.util.di.ComputationDispatcher
import tech.mujtaba.entain.feature.race.domain.RaceCategory
import tech.mujtaba.entain.feature.race.domain.usecase.ObserveNextRacesUsecase

@HiltViewModel
internal class RaceViewmodel @Inject constructor(
    private val observeNextRacesUsecase: ObserveNextRacesUsecase,
    private val summaryMapper: SummaryMapper,
    private val dateTimeHelper: DateTimeHelper,
    @ComputationDispatcher
    private val computationDispatcher: CoroutineDispatcher
) : ViewModel() {

    companion object {
        const val REFRESH_COUNTDOWN_TIME_IN_SECONDS = 1L
    }

    private val _state: MutableStateFlow<State> = MutableStateFlow(State.Error)
    val state = _state.asStateFlow()

    fun startTimer() {
        viewModelScope.launch(computationDispatcher) {
            while (true) {
                val currentState = state.value
                if (currentState !is State.Success) {
                    yield()
                    continue
                }
                _state.value = currentState.copy(
                    allSummaries = currentState.allSummaries.map {
                        it.copy(
                            timeToStartInSeconds = dateTimeHelper.timeDifferenceInSeconds(
                                it.advertisedStart
                            )
                        )
                    }
                )
                delay(REFRESH_COUNTDOWN_TIME_IN_SECONDS * 1000)
            }
        }
    }

    fun startFetchingRaces() {
        viewModelScope.launch(computationDispatcher) {
            observeNextRacesUsecase().collectLatest { result ->
                when {
                    result.isFailure && state.value !is State.Success -> _state.value = State.Error
                    result.isSuccess -> {
                        val allUiSummaries = result.getOrThrow().map(summaryMapper::map)
                        val currentState = state.value
                        val enabledCategories = if (currentState is State.Success) {
                            currentState.enabledCategories
                        } else {
                            RaceCategory.entries.toSet()
                        }
                        _state.value =
                            State.Success(allUiSummaries, enabledCategories)
                    }
                }
            }
        }
    }

    fun toggleCategory(category: RaceCategory) {
        val currentState = state.value as? State.Success ?: return

        viewModelScope.launch(computationDispatcher) {
            val newCategories = currentState.enabledCategories.toMutableSet()
            when {
                newCategories.contains(category) -> newCategories.remove(category)
                else -> newCategories.add(category)
            }
            _state.value = currentState.copy(
                enabledCategories = newCategories
            )
        }
    }

    sealed interface State {
        data object Loading : State
        data object Error : State
        data class Success(
            val allSummaries: List<RaceUiSummary>,
            val enabledCategories: Set<RaceCategory>
        ) : State {
            val allCategories = RaceCategory.entries
            val filteredSummaries: List<RaceUiSummary> =
                allSummaries.filter { enabledCategories.contains(it.category) }
        }
    }
}
