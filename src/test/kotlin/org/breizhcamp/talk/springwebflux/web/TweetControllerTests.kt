package org.breizhcamp.talk.springwebflux.web

import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.whenever
import org.breizhcamp.talk.springwebflux.model.Tweet
import org.breizhcamp.talk.springwebflux.repository.TweetRepository
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.test.context.junit.jupiter.SpringExtension
import org.springframework.test.web.reactive.server.WebTestClient
import org.springframework.test.web.reactive.server.expectBody
import org.springframework.web.reactive.function.client.WebClientResponseException
import reactor.core.publisher.Mono
import java.time.Instant

@ExtendWith(SpringExtension::class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class TweetControllerTests {

    @MockBean
    private lateinit var tweetRepository: TweetRepository

    @Autowired
    private lateinit var client: WebTestClient

    @Test
    fun `should return the tag for a given id`() {
        val tweet = Tweet(
                id = 1,
                hashtag = "test",
                content = "content",
                createdAt = Instant.now()
        )

        whenever(this.tweetRepository.findById(1))
                .thenReturn(Mono.just(tweet))

        this.client.get().uri("/tweets/1")
                .exchange()
                .expectStatus().isOk
                .expectBody<Tweet>().isEqualTo(tweet)
    }

    @Test
    fun `should return a 404 if tag does not exist`() {
        whenever(this.tweetRepository.findById(1))
                .thenReturn(Mono.error(mock<WebClientResponseException.NotFound> {  }))

        this.client.get().uri("/tweets/1")
                .exchange()
                .expectStatus().isNotFound
    }

}
