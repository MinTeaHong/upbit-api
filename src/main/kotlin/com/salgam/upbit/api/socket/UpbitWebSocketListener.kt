package com.salgam.upbit.api.socket

import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import com.google.gson.FieldNamingPolicy
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.JsonObject
import com.salgam.upbit.api.socket.dto.UpbitTickerDto
import com.salgam.upbit.api.socket.message.OnMessageActionInterface
import okhttp3.*
import okio.ByteString
import org.slf4j.LoggerFactory
import java.util.*
import java.util.concurrent.TimeUnit


class UpbitWebSocketListener(val action: OnMessageActionInterface) : WebSocketListener() {

    companion object {
        private val logger = LoggerFactory.getLogger(UpbitWebSocketListener::class.java)
    }

    private val gson: Gson = GsonBuilder()
        .setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
        .create()

    override fun onOpen(webSocket: WebSocket, response: Response) {
        logger.info("[UpbitWebSocketListener] Connected to WebSocket server")
    }

    override fun onMessage(webSocket: WebSocket, text: String) {
        logger.info("[UpbitWebSocketListener]  Received String message: $text")
    }

    override fun onMessage(webSocket: WebSocket, bytes: ByteString) {
        val message = bytes.string(Charsets.UTF_8)
        logger.debug("[UpbitWebSocketListener] Received byte message: ${bytes.string(Charsets.UTF_8)}")
        val jsonObject = Gson().fromJson(message, JsonObject::class.java)
        if(jsonObject.get("type").asString == "ticker") {
            val upbitTickerDto = gson.fromJson(jsonObject, UpbitTickerDto::class.java)
            action.tickerAction(upbitTickerDto)
        }

    }


    override fun onClosed(webSocket: WebSocket, code: Int, reason: String) {
        logger.info("[UpbitWebSocketListener] Connection closed: $reason")
    }

    override fun onFailure(webSocket: WebSocket, t: Throwable, response: Response?) {
        logger.error("[UpbitWebSocketListener] Connection error throw, -- ", t )
        logger.error("[UpbitWebSocketListener] Connection error response -- ", response )
    }

    fun makeWebsocket(accessKey : String, secretKey: String) : WebSocket{

        val algorithm: Algorithm = Algorithm.HMAC256(secretKey)
        val jwtToken: String = JWT.create()
                .withClaim("access_key", accessKey)
                .withClaim("nonce", UUID.randomUUID().toString())
                .withClaim("query_hash_alg", "SHA512")
                .sign(algorithm)


        val client = OkHttpClient.Builder()
                .readTimeout(3, TimeUnit.SECONDS)
                .build()

        val request = Request.Builder()
                .url("wss://api.upbit.com/websocket/v1")
                .addHeader("Authorization", "Bearer $jwtToken")
                .build()
        return  client.newWebSocket(request, this)
    }

}
