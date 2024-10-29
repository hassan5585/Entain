package tech.mujtaba.entain.feature.race.data

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class AdvertisedStart(
    @SerialName("seconds")
    val seconds: Long
)
