package de.justjanne.voctotv.voctoweb.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class LiveRoomTalksModel(
    @SerialName("current")
    val current: LiveTalkModel?,
    @SerialName("next")
    val next: LiveTalkModel?,
)
