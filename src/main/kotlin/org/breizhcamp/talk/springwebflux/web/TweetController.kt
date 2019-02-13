package org.breizhcamp.talk.springwebflux.web

import org.breizhcamp.talk.springwebflux.model.HashtagCount
import org.breizhcamp.talk.springwebflux.model.Tweet
import org.breizhcamp.talk.springwebflux.service.TweetService
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.http.server.reactive.ServerHttpResponse
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.reactive.function.client.WebClientResponseException
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import reactor.core.publisher.onErrorResume

@RestController
@RequestMapping("/api")
class TweetController(private val tweetService: TweetService) {

    @GetMapping(path = ["/tweets/{id}"])
    fun getTweet(@PathVariable id: Long, serverHttpResponse: ServerHttpResponse): Mono<Tweet> {
        return this.tweetService.getTweet(id)
                .onErrorResume(WebClientResponseException.NotFound::class) {
                    serverHttpResponse.statusCode = HttpStatus.NOT_FOUND
                    Mono.empty()
                }
    }

    @GetMapping(path = ["/hashtag-popularity"], produces = [MediaType.TEXT_EVENT_STREAM_VALUE])
    fun countByHashTag(): Flux<HashtagCount> {
        return this.tweetService.countTweetsByTagsStream()
    }

}
