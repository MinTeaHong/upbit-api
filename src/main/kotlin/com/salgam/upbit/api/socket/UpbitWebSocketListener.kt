package com.salgam.upbit.api.socket

import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import com.google.gson.FieldNamingPolicy
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.JsonObject
import com.salgam.upbit.api.socket.dto.UpbitTickerDto
import okhttp3.*
import okio.ByteString
import org.slf4j.LoggerFactory
import java.util.*
import java.util.concurrent.TimeUnit


class UpbitWebSocketListener(
    val onMessageTickerAction: (UpbitTickerDto) -> Unit
) : WebSocketListener() {

    companion object {
        private val logger = LoggerFactory.getLogger(UpbitWebSocketListener::class.java)
    }

    private val gson: Gson = GsonBuilder()
        .setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
        .create()

    override fun onOpen(webSocket: WebSocket, response: Response) {
        logger.debug("[UpbitWebSocketListener] Connected to WebSocket server")
    }

    override fun onMessage(webSocket: WebSocket, text: String) {
        logger.debug("[UpbitWebSocketListener]  Received String message: $text")
    }

    override fun onMessage(webSocket: WebSocket, bytes: ByteString) {
        val message = bytes.string(Charsets.UTF_8)
        logger.debug("[UpbitWebSocketListener] Received byte message: ${bytes.string(Charsets.UTF_8)}")
        val jsonObject = Gson().fromJson(message, JsonObject::class.java)
        if(jsonObject.get("type").asString == "ticker") {
            val upbitTickerDto = gson.fromJson(jsonObject, UpbitTickerDto::class.java)
            onMessageTickerAction(upbitTickerDto)
        }
    }


    override fun onClosed(webSocket: WebSocket, code: Int, reason: String) {
        logger.debug("[UpbitWebSocketListener] Connection closed: $reason")
    }

    override fun onFailure(webSocket: WebSocket, t: Throwable, response: Response?) {
        logger.error("[UpbitWebSocketListener] Connection error throw, -- ", t )
        logger.error("[UpbitWebSocketListener] Connection error response -- ", response )
    }

    fun makeWebsocket(accessKey: String, secretKey: String, webSocketUl: String): WebSocket {

        val algorithm: Algorithm = Algorithm.HMAC256(secretKey)
        val jwtToken: String = JWT.create()
                .withClaim("access_key", accessKey)
                .withClaim("nonce", UUID.randomUUID().toString())
                .withClaim("query_hash_alg", "SHA512")
                .sign(algorithm)

        val client = OkHttpClient.Builder()
            .readTimeout(5, TimeUnit.SECONDS)
            .connectTimeout(5, TimeUnit.SECONDS)
                .build()

        val request = Request.Builder()
            .url(webSocketUl)
                .addHeader("Authorization", "Bearer $jwtToken")
                .build()
        return  client.newWebSocket(request, this)
    }

}
