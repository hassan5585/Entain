package tech.mujtaba.entain.feature.race.data

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
internal data class NextRacesResponse(
    @SerialName("status")
    val status: Int,
    @SerialName("data")
    val data: Data,
    @SerialName("message")
    val message: String
)
