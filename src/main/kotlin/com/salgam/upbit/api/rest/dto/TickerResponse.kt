package com.salgam.upbit.api.rest.dto

import com.fasterxml.jackson.annotation.JsonProperty
import java.math.BigDecimal

data class TickerResponse(
    @JsonProperty("market") val market: String,
    @JsonProperty("candle_date_time_utc") val candleDateTimeUtc: String,
    @JsonProperty("candle_date_time_kst") val candleDateTimeKst: String,
    @JsonProperty("opening_price") val openingPrice: BigDecimal,
    @JsonProperty("high_price") val highPrice: BigDecimal,
    @JsonProperty("low_price") val lowPrice: BigDecimal,
    @JsonProperty("trade_price") val tradePrice: BigDecimal,
    @JsonProperty("timestamp") val timestamp: Long,
    @JsonProperty("candle_acc_trade_price") val candleAccTradePrice: BigDecimal,
    @JsonProperty("candle_acc_trade_volume") val candleAccTradeVolume: BigDecimal,
    @JsonProperty("unit") val unit: Int
)
