package com.example.liliangpt.repository

import com.example.liliangpt.model.request.BodyToSend
import com.example.liliangpt.model.response.GeneratedAnswer
import com.example.liliangpt.service.RetrofitInstance

class OpenAiRepository {
    private val openAiService = RetrofitInstance.openAiService

    suspend fun getChatFromOpenAi(bodyToSend: BodyToSend): GeneratedAnswer {
        return openAiService.getMessages(bodyToSend)
    }
}