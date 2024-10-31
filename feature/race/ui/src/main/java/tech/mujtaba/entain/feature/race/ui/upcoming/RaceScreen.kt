package tech.mujtaba.entain.feature.race.ui.upcoming

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavBackStackEntry
import java.time.LocalDateTime
import tech.mujtaba.entain.core.ui.theme.EntainTheme
import tech.mujtaba.entain.feature.race.domain.RaceCategory
import tech.mujtaba.entain.feature.race.ui.R
import tech.mujtaba.entain.feature.race.ui.helper.title

@Composable
internal fun RaceScreen(backStackEntry: NavBackStackEntry) {
    val viewmodel: RaceViewmodel = hiltViewModel()
    LaunchedEffect(key1 = viewmodel) {
        with(viewmodel) {
            startFetchingRaces()
            startTimer()
        }
    }
    val state by viewmodel.state.collectAsState()
    RaceContent(
        state = state,
        toggleCategory = viewmodel::toggleCategory
    )
}

@Composable
private fun RaceContent(state: RaceViewmodel.State, toggleCategory: (RaceCategory) -> Unit) {
    Scaffold(modifier = Modifier.fillMaxSize()) { paddingValues ->
        Box(
            modifier = Modifier
                .padding(
                    top = paddingValues.calculateTopPadding(),
                    bottom = paddingValues.calculateBottomPadding()
                )
                .fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            when (state) {
                RaceViewmodel.State.Loading -> LoadingState()
                RaceViewmodel.State.Error -> ErrorState()
                is RaceViewmodel.State.Success -> SuccessState(state, toggleCategory)
            }
        }
    }
}

@Composable
private fun SuccessState(state: RaceViewmodel.State.Success, toggleCategory: (RaceCategory) -> Unit) {
    Column(modifier = Modifier.fillMaxSize()) {
        Row {
            state.allCategories.forEach { toggleCategory ->
                Column(
                    modifier = Modifier.weight(1f),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(text = toggleCategory.title())
                    Checkbox(
                        checked = state.enabledCategories.contains(toggleCategory),
                        onCheckedChange = {
                            toggleCategory(toggleCategory)
                        }
                    )
                }
            }
        }

        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(state.filteredSummaries) { item: RaceUiSummary ->
                RaceSummaryCell(summary = item)
            }
        }
    }
}

@Composable
private fun RaceSummaryCell(summary: RaceUiSummary) {
    Card(
        modifier = Modifier
            .padding(horizontal = 16.dp)
            .fillMaxWidth()
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Text(
                text = summary.name,
                style = MaterialTheme.typography.bodyLarge.copy(
                    fontWeight = FontWeight.Bold
                )
            )
            val timeToStartTitle = if (summary.timeToStartInSeconds > 0) {
                stringResource(id = R.string.card_starts_in_future, summary.timeToStartInSeconds)
            } else {
                stringResource(id = R.string.card_starts_in_past)
            }
            Text(
                text = timeToStartTitle,
                style = MaterialTheme.typography.bodyLarge
            )
            Text(
                text = stringResource(id = R.string.card_start_time, summary.startDateTime),
                style = MaterialTheme.typography.bodyLarge
            )
            Text(
                text = stringResource(id = R.string.card_start_venue, summary.venueName, summary.venueState),
                style = MaterialTheme.typography.bodyLarge
            )
            Text(
                text = stringResource(id = R.string.card_meeting_name, summary.meetingName),
                style = MaterialTheme.typography.bodyLarge
            )
            Text(
                text = stringResource(id = R.string.card_race_number, summary.number),
                style = MaterialTheme.typography.bodyLarge
            )
        }
    }
}

@Composable
private fun LoadingState() {
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        CircularProgressIndicator(
            modifier = Modifier.size(100.dp)
        )
    }
}

@Composable
private fun ErrorState() {
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Text(text = stringResource(id = R.string.error_screen_title))
    }
}

@Preview(showBackground = true)
@Composable
private fun ErrorStatePreview() {
    EntainTheme {
        Surface {
            ErrorState()
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun SuccessStatePreview() {
    EntainTheme {
        Surface {
            val summary = RaceUiSummary(
                "id", "RaceName", 10, "MCG", "VIC",
                "Oct 29, 2024, 11:09:00 PM", RaceCategory.HORSE, 100L, LocalDateTime.now(),
                "Meeting Name"
            )
            SuccessState(
                state = RaceViewmodel.State.Success(
                    listOf(
                        summary.copy(name = "Name1"),
                        summary.copy(name = "Name2", timeToStartInSeconds = 0L),
                        summary.copy(name = "Name3")
                    ),
                    RaceCategory.entries.toSet()
                )
            ) {
            }
        }
    }
}
