package org.breizhcamp.talk.springwebflux.web

import org.breizhcamp.talk.springwebflux.model.Tweet
import org.breizhcamp.talk.springwebflux.service.TweetService
import org.springframework.http.HttpStatus
import org.springframework.http.server.reactive.ServerHttpResponse
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.reactive.function.client.WebClientResponseException
import reactor.core.publisher.Mono

@RestController
class TweetController(private val tweetService: TweetService) {


    @GetMapping("tweets/{id}")
    fun getTweet(@PathVariable id: Long, serverHttpResponse: ServerHttpResponse): Mono<Tweet> {
        return tweetService
                .getTweet(id)
                .onErrorResume(WebClientResponseException.NotFound::class.java) {
                    serverHttpResponse.statusCode = HttpStatus.NOT_FOUND
                    Mono.empty()
                }
    }

}