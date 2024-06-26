package com.salgam.upbit.api.unit.dummy

import com.salgam.upbit.api.socket.dto.UpbitTickerDto
import com.salgam.upbit.api.socket.message.OnMessageActionInterface
import org.slf4j.LoggerFactory

class DummyAction : OnMessageActionInterface {
    override fun tickerAction(upbitTickerDto:UpbitTickerDto) {
        logger.info("tickerAction go : {}", upbitTickerDto)
    }
    companion object {
        private val logger = LoggerFactory.getLogger(DummyAction::class.java)
    }
}