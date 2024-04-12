package com.example.liliangpt.model.response

import com.google.gson.annotations.SerializedName
import com.example.liliangpt.model.OpenAiMessageBody

data class Choice(
    @SerializedName("message")
    val message: OpenAiMessageBody
)