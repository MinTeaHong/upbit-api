package com.salgam.upbit.api.socket.message

import com.salgam.upbit.api.socket.dto.UpbitTickerDto

interface OnMessageActionInterface {
    fun tickerAction(upbitTickerDto:UpbitTickerDto);
}