package org.breizhcamp.talk.springwebflux.repository

import org.breizhcamp.talk.springwebflux.model.Tweet
import org.springframework.stereotype.Repository
import org.springframework.web.reactive.function.client.WebClient
import org.springframework.web.reactive.function.client.bodyToFlux
import org.springframework.web.reactive.function.client.bodyToMono
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

interface TweetRepository {

    fun findById(id: Long): Mono<Tweet>

    fun findAll(): Flux<Tweet>

}

@Repository
class TweetRepositoryHttpImpl(private val webClient: WebClient): TweetRepository {

    override fun findById(id: Long): Mono<Tweet> {
        return webClient
                .get()
                .uri("/tweets/$id")
                .retrieve()
                .bodyToMono()
    }

    override fun findAll(): Flux<Tweet> {
        return webClient
                .get()
                .uri("/tweets")
                .retrieve()
                .bodyToFlux()
    }

}