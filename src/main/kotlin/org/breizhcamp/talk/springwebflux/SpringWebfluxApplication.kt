package org.breizhcamp.talk.springwebflux

import org.breizhcamp.talk.springwebflux.repository.TweetRepository
import org.breizhcamp.talk.springwebflux.repository.TweetRepositoryImpl
import org.breizhcamp.talk.springwebflux.service.TweetService
import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.CommandLineRunner
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.Bean
import org.springframework.web.reactive.function.client.WebClient

@SpringBootApplication
class SpringWebfluxApplication {

    @Bean
    fun commandLineRunner(tweetService: TweetService): CommandLineRunner {
        return CommandLineRunner {
            tweetService.countTweetsByTagsStream()
                    .log()
                    .subscribe()
        }
    }

    @Bean
    fun webClient(@Value("\${tweet-service.base-url}") tweetServiceBaseUrl: String): WebClient {
        return WebClient.create(tweetServiceBaseUrl)
    }

    @Bean
    fun tweetRepository(webClient: WebClient): TweetRepository {
        return TweetRepositoryImpl(webClient)
    }

}

fun main(args: Array<String>) {
    runApplication<SpringWebfluxApplication>(*args)
}

