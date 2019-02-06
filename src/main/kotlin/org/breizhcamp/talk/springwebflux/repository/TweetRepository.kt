package org.breizhcamp.talk.springwebflux.repository

import org.breizhcamp.talk.springwebflux.model.Tweet
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

interface TweetRepository {

    fun findById(id: Long): Mono<Tweet>

    fun findAll(): Flux<Tweet>

    fun stream(): Flux<Tweet>

}
