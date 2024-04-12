package com.example.liliangpt.service

import com.example.liliangpt.model.request.BodyToSend
import com.example.liliangpt.model.response.GeneratedAnswer
import retrofit2.http.Body
import retrofit2.http.POST

interface OpenAiService {
    @POST("chat/completions")
    suspend fun getMessages(@Body post: BodyToSend): GeneratedAnswer
}