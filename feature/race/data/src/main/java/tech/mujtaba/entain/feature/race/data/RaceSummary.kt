package tech.mujtaba.entain.feature.race.data

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
internal data class RaceSummary(
    @SerialName("race_id")
    val id: String,
    @SerialName("race_name")
    val name: String,
    @SerialName("race_number")
    val number: Int,
    @SerialName("meeting_id")
    val meetingId: String,
    @SerialName("meeting_name")
    val meetingName: String,
    @SerialName("advertised_start")
    val advertisedStart: AdvertisedStart,
    @SerialName("category_id")
    val categoryId: String,
    @SerialName("venue_name")
    val venueName: String,
    @SerialName("venue_state")
    val venueState: String
)
