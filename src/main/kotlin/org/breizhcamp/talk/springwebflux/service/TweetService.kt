package org.breizhcamp.talk.springwebflux.service

import org.breizhcamp.talk.springwebflux.repository.TweetRepository
import org.springframework.stereotype.Service
import reactor.core.publisher.Flux

@Service
class TweetService(private val tweetRepository: TweetRepository) {

    fun getTweet(id: Long) = tweetRepository.findById(id)

    fun getTweetContentByTag(tag: String): Flux<String> {

        return tweetRepository
                .findAll()
                .filter { tweet -> tweet.hashtag == tag }
                .map { tweet -> tweet.content }
    }

}