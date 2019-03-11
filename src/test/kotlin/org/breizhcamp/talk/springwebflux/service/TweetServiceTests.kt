package org.breizhcamp.talk.springwebflux.service

import com.fasterxml.jackson.module.kotlin.readValue
import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.whenever
import org.breizhcamp.talk.springwebflux.model.Tweet
import org.breizhcamp.talk.springwebflux.repository.TweetRepository
import org.breizhcamp.talk.springwebflux.serialization.OBJECT_MAPPER
import org.junit.jupiter.api.Test
import reactor.core.publisher.Flux
import reactor.test.StepVerifier

class TweetServiceTests {

    private val tweetRepository = mock<TweetRepository> {  }

    private val tweetService = TweetService(tweetRepository)

    @Test
    fun `should get tweet contents by tag`() {
        whenever(this.tweetRepository.findAll())
                .thenReturn(Flux.fromIterable(OBJECT_MAPPER.readValue<List<Tweet>>(this.javaClass.getResourceAsStream("/tweets.json"))))

        StepVerifier.create(this.tweetService.getTweetContentByTag("kotlin"))
                .expectNext("Kotlin 1.3.21 has arrived.")
                .verifyComplete()
    }

}
