package com.example.customchatbot

import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.Headers
import retrofit2.http.POST

interface ClovaChatbotService {
    @Headers(
        "Content-Type: application/json",
        "x-ncp-apigw-timestamp: TIMESTAMP",
        "X-NCP-CHATBOT_SIGNATURE: ek1uhid8xd"
    )
    @POST("ncc/chitchat")
    fun sendChatMessage(
        @Header("x-ncp-iam-access-key") accessKey: String,
        @Body request: ChatbotRequest
    ): Call<ChatbotResponse>
}