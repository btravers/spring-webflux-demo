package org.breizhcamp.talk.springwebflux.repository

import org.breizhcamp.talk.springwebflux.model.Tweet
import org.springframework.stereotype.Repository
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

interface TweetRepository {

    fun findById(id: Long): Mono<Tweet>

    fun findAll(): Flux<Tweet>

}

@Repository
class TweetRepositoryImpl: TweetRepository {

    override fun findById(id: Long): Mono<Tweet> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun findAll(): Flux<Tweet> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

}
