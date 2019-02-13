package org.breizhcamp.talk.springwebflux.repository

import org.breizhcamp.talk.springwebflux.model.Tweet
import org.springframework.http.MediaType
import org.springframework.web.reactive.function.client.WebClient
import org.springframework.web.reactive.function.client.bodyToFlux
import org.springframework.web.reactive.function.client.bodyToMono
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

class TweetRepositoryImpl(private val webClient: WebClient): TweetRepository {

    override fun stream(): Flux<Tweet> {
        return this.webClient.get()
                .uri("/tweets")
                .accept(MediaType.TEXT_EVENT_STREAM)
                .retrieve()
                .bodyToFlux()
    }

    override fun findAll(): Flux<Tweet> {
        return this.webClient.get()
                .uri("/tweets")
                .retrieve()
                .bodyToFlux()
    }

    override fun findById(id: Long): Mono<Tweet> {
        return this.webClient.get()
                .uri("/tweets/$id")
                .retrieve()
                .bodyToMono()
    }

}
