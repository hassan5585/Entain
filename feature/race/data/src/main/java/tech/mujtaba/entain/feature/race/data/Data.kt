package tech.mujtaba.entain.feature.race.data

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
internal data class Data(
    @SerialName("next_to_go_ids")
    val nextToGoIds: List<String>,
    @SerialName("race_summaries")
    val raceSummaries: Map<String, RaceSummary>
)
