package com.salgam.upbit.api.socket.message

import com.google.gson.Gson
import com.google.gson.JsonArray
import com.google.gson.JsonObject
import java.util.Objects

class UpbitSendMessageFactory {

    fun makeTickerMessage(ticketValue: String, pairList : List<String>, format: String? = null): String {
        val json = JsonArray().apply {
            add(JsonObject().apply {
                addProperty("ticket", ticketValue)
            })
            add(JsonObject().apply {
                addProperty("type", "ticker")
                add("codes", JsonArray().apply {
                    for (pair in pairList) {
                        add(pair)
                    }
                })
            })
            if (Objects.nonNull(format)) {
                add(JsonObject().apply {
                    addProperty("format", format)
                })
            }
        }

        return Gson().toJson(json)
    }
}