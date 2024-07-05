package com.salgam.upbit.api.rest

import com.salgam.upbit.api.rest.dto.TickerResponse
import com.salgam.upbit.api.rest.enum.Period
import org.slf4j.LoggerFactory
import org.springframework.web.reactive.function.client.WebClient
import org.springframework.web.reactive.function.client.bodyToMono


class UpbitRestApi(val url: String) {

  companion object {
    private val logger = LoggerFactory.getLogger(UpbitRestApi::class.java)
  }

  private val webClient: WebClient = WebClient.builder()
      .baseUrl(url)
      .build()

  fun getTickerPrice(ticker: String, period: Period, count: Long,
                     onSuccess: (List<TickerResponse>) -> Unit,
                     onError: (Throwable) -> Unit) {
    webClient.get()
        .uri { uriBuilder ->
          uriBuilder.path("/v1/candles/${period.urlName}")
              .queryParam("market", ticker)
              .queryParam("count", count)
              .build()
        }
        .retrieve()
        .bodyToMono<List<TickerResponse>>()
        .subscribe(onSuccess, onError)
  }

}