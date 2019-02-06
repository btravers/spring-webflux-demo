package org.breizhcamp.talk.springwebflux

import org.breizhcamp.talk.springwebflux.repository.TweetRepository
import org.springframework.boot.CommandLineRunner
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.Bean

@SpringBootApplication
class SpringWebfluxApplication {

    @Bean
    fun commandLineRunner(tweetRepository: TweetRepository): CommandLineRunner {
        return CommandLineRunner {
            tweetRepository.findAll()
                    .map { tweet -> tweet.hashtag }
                    .subscribe(::println)

            tweetRepository.findById(33875)
                    .subscribe(::println)
        }
    }

}

fun main(args: Array<String>) {
    runApplication<SpringWebfluxApplication>(*args)
}

