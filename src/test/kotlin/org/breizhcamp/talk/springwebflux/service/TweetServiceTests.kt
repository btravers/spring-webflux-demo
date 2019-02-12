package org.breizhcamp.talk.springwebflux.service

import com.fasterxml.jackson.module.kotlin.readValue
import com.nhaarman.mockito_kotlin.verify
import com.nhaarman.mockito_kotlin.whenever
import org.breizhcamp.talk.springwebflux.model.HashtagCount
import org.breizhcamp.talk.springwebflux.model.Tweet
import org.breizhcamp.talk.springwebflux.repository.TweetRepository
import org.breizhcamp.talk.springwebflux.serialization.OBJECT_MAPPER
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.junit.jupiter.MockitoExtension
import reactor.core.publisher.toFlux
import reactor.test.StepVerifier

@Suppress("UnassignedFluxMonoInstance")
@ExtendWith(MockitoExtension::class)
class TweetServiceTests {

    @Mock
    private lateinit var tweetRepository: TweetRepository

    @InjectMocks
    private lateinit var tweetService: TweetService

    @Test
    fun `should count number of tweets per hashtag`() {
        val tweets = OBJECT_MAPPER.readValue<List<Tweet>>(this.javaClass.getResourceAsStream("/tweets.json")).toFlux()

        whenever(this.tweetRepository.findAll())
                .thenReturn(tweets)

        StepVerifier.create(this.tweetService.countTweetsByTags())
                .recordWith { mutableListOf() }
                .thenConsumeWhile { true }
                .expectRecordedMatches { items ->
                    items.size == 5 && items.containsAll(listOf(
                            HashtagCount(hashtag = "java", count = 2L),
                            HashtagCount(hashtag = "javascript", count = 1L),
                            HashtagCount(hashtag = "kotlin", count = 1L),
                            HashtagCount(hashtag = "pilotage", count = 1L),
                            HashtagCount(hashtag = "php", count = 1L)
                    ))
                }
                .verifyComplete()

        verify(this.tweetRepository).findAll()
    }

}
