package org.breizhcamp.talk.springwebflux.config

import org.breizhcamp.talk.springwebflux.serialization.OBJECT_MAPPER
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.server.ServerWebExchange
import org.springframework.web.server.WebFilter
import org.springframework.web.server.WebFilterChain
import reactor.core.publisher.Mono

@Configuration
class WebConfig {

    @Bean
    fun mapper() = OBJECT_MAPPER

    @Bean
    fun webFilter(): WebFilter {
        return object: WebFilter {
            override fun filter(exchange: ServerWebExchange, chain: WebFilterChain): Mono<Void> {
                exchange.response
                        .headers
                        .add("Cache-Control", "no-transform")
                return chain.filter(exchange)
            }
        }
    }

}
