package org.breizhcamp.talk.springwebflux

import org.slf4j.LoggerFactory
import org.springframework.boot.CommandLineRunner
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.Bean

@SpringBootApplication
class SpringWebfluxApplication {

    private val logger = LoggerFactory.getLogger(SpringWebfluxApplication::class.java)

    @Bean
    fun commandLineRunner() = CommandLineRunner {

    }

}

fun main(args: Array<String>) {
    runApplication<SpringWebfluxApplication>(*args)
}

