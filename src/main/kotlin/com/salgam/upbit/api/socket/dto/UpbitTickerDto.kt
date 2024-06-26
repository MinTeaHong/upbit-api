package com.salgam.upbit.api.socket.dto

import java.math.BigDecimal

data class UpbitTickerDto (
    val type: String,
    val code: String,
    val openingPrice: BigDecimal,
    val highPrice: BigDecimal,
    val lowPrice: BigDecimal,
    val tradePrice: BigDecimal,
    val prevClosingPrice: BigDecimal,
    val accTradePrice: BigDecimal,
    val change: String,
    val changePrice: BigDecimal,
    val signedChangePrice: BigDecimal,
    val changeRate: BigDecimal,
    val signedChangeRate: BigDecimal,
    val askBid: String,
    val tradeVolume: BigDecimal,
    val accTradeVolume: BigDecimal,
    val tradeDate: String,
    val tradeTime: String,
    val tradeTimestamp: Long,
    val accAskVolume: BigDecimal,
    val accBidVolume: BigDecimal,
    val highest52WeekPrice: BigDecimal,
    val highest52WeekDate: String,
    val lowest52WeekPrice: BigDecimal,
    val lowest52WeekDate: String,
    val marketState: String,
    val isTradingSuspended: Boolean,
    val delistingDate: String?,
    val marketWarning: String,
    val timestamp: Long,
    val accTradePrice24h: BigDecimal,
    val accTradeVolume24h: BigDecimal,
    val streamType: String

)