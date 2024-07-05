package com.salgam.upbit.api.unit

import com.salgam.upbit.api.rest.UpbitRestApi
import com.salgam.upbit.api.rest.enum.Period
import com.salgam.upbit.api.socket.UpbitWebSocketListener
import com.salgam.upbit.api.socket.message.UpbitSendMessageFactory
import org.junit.jupiter.api.Test
import org.slf4j.LoggerFactory
import java.util.concurrent.CountDownLatch

class unitTest {
    companion object {
        private val logger = LoggerFactory.getLogger(unitTest::class.java)
    }
    @Test
    fun 웹소켓_테스트() {

        val listener = UpbitWebSocketListener(
            onMessageTickerAction = { upbitTickerDto ->
                logger.info("tickerAction go : {}", upbitTickerDto)
            }
        )

        val accessKey = System.getenv("UPBIT_ACCESS_KEY")
        val secretKey = System.getenv("UPBIT_SECRET_KEY")
        val webSocketUrl = "wss://api.upbit.com/websocket/v1"

        val latch = CountDownLatch(1)

        val webSocket = listener.makeWebsocket(accessKey, secretKey, webSocketUrl)

        val upbitSendMessageFactory = UpbitSendMessageFactory()
        webSocket.send(upbitSendMessageFactory.makeTickerMessage("test",
            listOf("KRW-BTC", "KRW-ETH")
        ))

        latch.await()
    }

    @Test
    fun REST_API_테스트() {
        val upbitApiClient = UpbitRestApi("https://api.upbit.com")
        val ticker = "KRW-BTC"
        val count = 200L

        upbitApiClient.getTickerPrice(
            ticker, Period.OneMinuit, count,
            onSuccess = { tickerResponse ->
                if (tickerResponse.isNotEmpty()) {
                    val priceInfo = tickerResponse[0]
                    logger.info("Ticker: ${priceInfo.market}")
                    logger.info("Price: ${priceInfo.tradePrice}")
                    logger.info("Timestamp: ${priceInfo.timestamp}")
                } else {
                    logger.info("No data available")
                }
            },
            onError = { error ->
                logger.info("Error: ${error.message}")
            }
        )

    }

}