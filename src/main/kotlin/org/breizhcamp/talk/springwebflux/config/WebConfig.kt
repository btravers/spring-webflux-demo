package org.breizhcamp.talk.springwebflux.config

import org.breizhcamp.talk.springwebflux.serialization.OBJECT_MAPPER
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.reactive.function.client.WebClient
import org.springframework.web.server.WebFilter

@Configuration
class WebConfig {

    @Bean
    fun mapper() = OBJECT_MAPPER

    @Bean
    fun webFilter(): WebFilter {
        return WebFilter { exchange, chain ->
            exchange.response
                    .headers
                    .add("Cache-Control", "no-transform")
            chain.filter(exchange)
        }
    }

    @Bean
    fun webClient(@Value("\${tweet-service.base-url}") tweetServiceBaseUrl: String): WebClient {
        return WebClient.create(tweetServiceBaseUrl)
    }

}
