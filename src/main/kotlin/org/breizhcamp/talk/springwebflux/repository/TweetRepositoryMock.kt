package org.breizhcamp.talk.springwebflux.repository

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import org.breizhcamp.talk.springwebflux.model.Tweet
import org.springframework.beans.factory.annotation.Value
import org.springframework.core.io.Resource
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import reactor.core.publisher.toFlux
import reactor.core.publisher.toMono
import java.time.Duration

class TweetRepositoryMock(
        private val mapper: ObjectMapper,
        @Value("classpath:tweets.json") private val tweetsResource: Resource
): TweetRepository {

    override fun findById(id: Long): Mono<Tweet> {
        return this.findAll()
                .filter{ tweet -> tweet.id == id }
                .toMono()
    }

    override fun findAll(): Flux<Tweet> {
        return this.mapper.readValue<List<Tweet>>(tweetsResource.inputStream).toFlux()
    }

    override fun stream(): Flux<Tweet> {
        return this.findAll()
                .delayElements(Duration.ofMillis(200))
                .repeat()
    }

}
