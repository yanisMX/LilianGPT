package com.example.liliangpt.model.request

import com.google.gson.annotations.SerializedName
import com.example.liliangpt.model.OpenAiMessageBody

data class BodyToSend(
    @SerializedName("messages") val messages: List<OpenAiMessageBody>,
    @SerializedName("model") val model: String = "gpt-3.5-turbo"
)