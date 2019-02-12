package org.breizhcamp.talk.springwebflux.service

import org.breizhcamp.talk.springwebflux.model.HashtagCount
import org.breizhcamp.talk.springwebflux.repository.TweetRepository
import org.springframework.stereotype.Service
import reactor.core.publisher.Flux
import java.time.Duration

@Service
class TweetService(private val tweetRepository: TweetRepository) {

    fun getTweetContentByTag(tag: String): Flux<String> {
        return this.tweetRepository.findAll()
                .filter { tweet -> tweet.hashtag == tag }
                .map { tweet -> tweet.content }
    }

    fun countTweetsByTags(): Flux<HashtagCount> {
        return this.tweetRepository.findAll()
                .groupBy { tweet -> tweet.hashtag }
                .flatMap { flux ->
                    flux.count()
                            .map { count ->
                                HashtagCount(
                                        hashtag = flux.key()!!,
                                        count = count
                                )
                            }
                }
    }

    fun countTweetsByTagsStream(refresh: Duration = Duration.ofSeconds(1)): Flux<HashtagCount> {
        return this.tweetRepository.stream()
                .groupBy { tweet -> tweet.hashtag }
                .flatMap { group ->
                    group.scan(HashtagCount(group.key()!!, 0L)) { acc, _ -> HashtagCount(group.key()!!, acc.count + 1) }
                            .filter { (_, count) -> count != 0L }
                            .buffer(refresh)
                            .filter { bucket -> bucket.isNotEmpty() }
                            .map { bucket -> bucket.last() }
                }

    }

}
