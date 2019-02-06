package org.breizhcamp.talk.springwebflux.model

import java.time.Instant

data class Tweet(
        val id: Long,
        val hashtag: String,
        val content: String,
        val createdAt: Instant
)
