package tech.mujtaba.entain.feature.race.ui.helper

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import tech.mujtaba.entain.feature.race.domain.RaceCategory
import tech.mujtaba.entain.feature.race.ui.R

@Composable
internal fun RaceCategory.title(): String {
    return stringResource(
        id = when (this) {
            RaceCategory.HORSE -> R.string.title_race_category_horse
            RaceCategory.HARNESS -> R.string.title_race_category_harness
            RaceCategory.GREYHOUND -> R.string.title_race_category_greyhound
        }
    )
}
