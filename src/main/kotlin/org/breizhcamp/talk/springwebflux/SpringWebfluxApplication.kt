package org.breizhcamp.talk.springwebflux

import org.reactivestreams.Publisher
import org.reactivestreams.Subscriber
import org.reactivestreams.Subscription
import org.slf4j.LoggerFactory
import org.springframework.boot.CommandLineRunner
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.Bean

@SpringBootApplication
class SpringWebfluxApplication {

    private val logger = LoggerFactory.getLogger(SpringBootApplication::class.java)

    @Bean
    fun commandLineRunner() = CommandLineRunner {
        MyPublisher(listOf(1, 2, 3))
                .subscribe(MySubscriber())

    }

    /**
     * Does not conform reactivestreams specification: [org.reactivestreams.Subscription.request] and
     * [org.reactivestreams.Subscription.cancel] methods should be able to deal with concurrent access.
     *
     * This class aims to demonstrate reactivestreams API.
     * For real uses, have a look on [reactor.core.publisher.Flux] or [reactor.core.publisher.Mono].
     */
    inner class MyPublisher(private val list: List<Int>): Publisher<Int> {
        override fun subscribe(s: Subscriber<in Int>) {
            val subscription = object: Subscription {
                private val iter = list.iterator()
                private var done = false

                override fun request(n: Long) {
                    var e = 0L

                    while(e != n) {
                        if (this.done)
                            return

                        if (!iter.hasNext()) {
                            this.done = true
                            s.onComplete()
                            return
                        }

                        s.onNext(iter.next())

                        e++
                    }
                }

                override fun cancel() {
                    this.done = true
                    s.onError(Exception("Canceled"))
                }

            }
            s.onSubscribe(subscription)
        }

    }

    inner class MySubscriber: Subscriber<Int> {
        private lateinit var subscription: Subscription

        override fun onSubscribe(s: Subscription) {
            logger.info("Subscribe")
            this.subscription = s
            this.subscription.request(1)
        }

        override fun onNext(t: Int) {
            logger.info("Next: {}", t)
            this.subscription.request(1)
        }

        override fun onComplete() {
            logger.info("Complete")
        }

        override fun onError(t: Throwable) {
            logger.error("Error", t)
        }

    }

}

fun main(args: Array<String>) {
    runApplication<SpringWebfluxApplication>(*args)
}

