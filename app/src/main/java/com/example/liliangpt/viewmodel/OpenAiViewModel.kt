package com.example.liliangpt.viewmodel

import android.util.Log
import androidx.compose.material3.Text
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.liliangpt.model.OpenAiMessageBody
import com.example.liliangpt.model.request.BodyToSend
import com.example.liliangpt.model.response.GeneratedAnswer
import com.example.liliangpt.repository.OpenAiRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class OpenAiViewModel : ViewModel() {
    private val repository = OpenAiRepository()
    private val _openAiResponse = MutableStateFlow<GeneratedAnswer?>(null)

    val openAiResponse: StateFlow<GeneratedAnswer?> = _openAiResponse

    fun clearConversation() {
        _openAiResponse.value = null

    }

    fun fetchMessages(messageList : MutableList<OpenAiMessageBody>) {
        viewModelScope.launch {
            try {
                val bodyToSend = BodyToSend(messages = messageList)

                val response = repository.getChatFromOpenAi(bodyToSend)
                messageList.add(response.choices.first().message)
               Log.e("Fetch Messages List", openAiResponse.toString())
            } catch (e: Exception) {
                // Handle error
                Log.e("Fetch Messages List NE MARCHE PAS", openAiResponse.toString())
            }
        }
    }
}