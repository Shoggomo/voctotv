package de.justjanne.voctotv.voctoweb.model

import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonContentPolymorphicSerializer
import kotlinx.serialization.json.JsonElement
import kotlinx.serialization.json.JsonIgnoreUnknownKeys
import kotlinx.serialization.json.contentOrNull
import kotlinx.serialization.json.jsonObject
import kotlinx.serialization.json.jsonPrimitive

@Serializable(with = LiveTalkModel.Serializer::class)
sealed interface LiveTalkModel {
    @OptIn(ExperimentalSerializationApi::class)
    @Serializable
    @JsonIgnoreUnknownKeys
    data class Gap(
        @SerialName("fstart")
        val startTimestamp: Timestamp,
        @SerialName("fend")
        val endTimestamp: Timestamp,
        @SerialName("tstart")
        val startText: String,
        @SerialName("tend")
        val endText: String,
        @SerialName("start")
        val startInstant: Int,
        @SerialName("end")
        val endInstant: Int,
        @SerialName("offset")
        val offset: Int,
        @SerialName("duration")
        val duration: Int,
    ) : LiveTalkModel

    @Serializable
    data class Talk(
        @SerialName("fstart")
        val startTimestamp: Timestamp,
        @SerialName("fend")
        val endTimestamp: Timestamp,
        @SerialName("tstart")
        val startText: String,
        @SerialName("tend")
        val endText: String,
        @SerialName("start")
        val startInstant: Int,
        @SerialName("end")
        val endInstant: Int,
        @SerialName("offset")
        val offset: Int,
        @SerialName("duration")
        val duration: Int,
        @SerialName("guid")
        val guid: String,
        @SerialName("code")
        val code: String,
        @SerialName("track")
        val track: String,
        @SerialName("title")
        val title: String,
        @SerialName("speaker")
        val speaker: String,
        @SerialName("room_known")
        val roomKnown: Boolean,
        @SerialName("optout")
        val optOut: Boolean,
        @SerialName("url")
        val url: String,
    ) : LiveTalkModel

    object Serializer : JsonContentPolymorphicSerializer<LiveTalkModel>(LiveTalkModel::class) {
        override fun selectDeserializer(element: JsonElement) =
            when (val kind = element.jsonObject["special"]?.jsonPrimitive?.contentOrNull) {
                null -> Talk.serializer()
                "gap" -> Gap.serializer()
                else -> error("Unknown live talk model: $kind")
            }
    }
}
