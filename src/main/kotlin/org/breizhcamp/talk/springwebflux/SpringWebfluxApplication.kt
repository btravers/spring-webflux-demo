package org.breizhcamp.talk.springwebflux

import org.breizhcamp.talk.springwebflux.service.TweetService
import org.springframework.boot.CommandLineRunner
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.Bean

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

}

fun main(args: Array<String>) {
    runApplication<SpringWebfluxApplication>(*args)
}

