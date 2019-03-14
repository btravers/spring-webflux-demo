package org.breizhcamp.talk.springwebflux.web

import org.breizhcamp.talk.springwebflux.service.TweetService
import org.springframework.web.bind.annotation.RestController

@RestController
class TweetController(private val tweetService: TweetService) {



}
