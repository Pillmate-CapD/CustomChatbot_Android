package com.example.customchatbot

import android.util.Base64
import org.json.JSONArray
import org.json.JSONObject
import java.io.BufferedReader
import java.io.DataOutputStream
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL
import java.util.Date
import javax.crypto.Mac
import javax.crypto.spec.SecretKeySpec

object ChatbotProc {
    fun main(voiceMessage: String, apiURL: String, secretKey: String): String {
        var chatbotMessage = ""

        try {
            val url = URL(apiURL)
            val message = getReqMessage(voiceMessage)
            println("## $message")

            val encodeBase64String = makeSignature(message, secretKey)

            val con = url.openConnection() as HttpURLConnection
            con.requestMethod = "POST"
            con.setRequestProperty("Content-Type", "application/json;UTF-8")
            con.setRequestProperty("X-NCP-CHATBOT_SIGNATURE", encodeBase64String)

            // post request
            con.doOutput = true
            val wr = DataOutputStream(con.outputStream)
            wr.write(message.toByteArray(charset("UTF-8")))
            wr.flush()
            wr.close()
            val responseCode = con.responseCode

            val br: BufferedReader

            chatbotMessage = if (responseCode == 200) { // Normal call
                println(con.responseMessage)

                val `in` = BufferedReader(InputStreamReader(con.inputStream))
                var decodedString: String?
                while (`in`.readLine().also { decodedString = it } != null) {
                    chatbotMessage = decodedString ?: ""
                }
                `in`.close()
                chatbotMessage
            } else {  // Error occurred
                con.responseMessage
            }
        } catch (e: Exception) {
            println(e)
        }

        return chatbotMessage
    }

    private fun makeSignature(message: String, secretKey: String): String {
        var encodeBase64String = ""

        try {
            val secrete_key_bytes = secretKey.toByteArray(charset("UTF-8"))

            val signingKey = SecretKeySpec(secrete_key_bytes, "HmacSHA256")
            val mac = Mac.getInstance("HmacSHA256")
            mac.init(signingKey)

            val rawHmac = mac.doFinal(message.toByteArray(charset("UTF-8")))
            encodeBase64String = Base64.encodeToString(rawHmac, Base64.NO_WRAP)

            return encodeBase64String

        } catch (e: Exception) {
            println(e)
        }

        return encodeBase64String
    }

    private fun getReqMessage(voiceMessage: String): String {
        var requestBody = ""

        try {
            val obj = JSONObject()

            val timestamp = Date().time
            println("##$timestamp")

            obj.put("version", "v2")
            obj.put("userId", "U47b00b58c90f8e47428af8b7bddc1231heo2")
            //=> userId is a unique code for each chat user, not a fixed value, recommend use UUID. use different id for each user could help you to split chat history for users.

            obj.put("timestamp", timestamp)

            val bubbles_obj = JSONObject()

            val data_obj = JSONObject()
            data_obj.put("description", voiceMessage)

            bubbles_obj.put("type", "text")
            bubbles_obj.put("data", data_obj)

            val bubbles_array = JSONArray()
            bubbles_array.put(bubbles_obj)

            obj.put("bubbles", bubbles_array)
            obj.put("event", "send")

            requestBody = obj.toString()

        } catch (e: Exception) {
            println("## Exception : $e")
        }

        return requestBody
    }
}