package org.stumblingoncode

import com.google.common.eventbus.AsyncEventBus
import spock.lang.Specification
import spock.util.concurrent.BlockingVariables

import java.util.concurrent.Executors


class AsyncEventBusSpec extends Specification {

    def "can publish a message"() {
        given: "An Async Event Bus"
        def eventBus = Mock(AsyncEventBus)

        and: "A Publisher"
        def publisher = new PersonPublisher(eventBus)

        and: "A Person Message"
        def personMessage = new PersonMessage(new Person(), PersonMessage.ACTION.CREATE)

        when:
        publisher.notifyChange(personMessage)

        then:
        1 * eventBus.post(personMessage)
    }

    def "can subscribe to a message"() {
        given: "An Async Event Bus"
        def eventBus = new AsyncEventBus(Executors.newCachedThreadPool())

        and: "A Person Message"
        def personMessage = new PersonMessage(new Person(), PersonMessage.ACTION.CREATE)

        and: "A blocking variable"
        def vars = new BlockingVariables()

        and: "A Fake Service"
        def fakeService = Stub(PersonService) {
            process(_) >> { PersonMessage message -> vars.message = message}
        }

        and: "A subscriber"
        def subscriber = new PersonSubscriber(eventBus, fakeService)

        when:
        eventBus.post(personMessage)

        then:
        vars.message == personMessage
    }

    def "this test should fail"() {
        given: "An Async Event Bus"
        def eventBus = new AsyncEventBus(Executors.newCachedThreadPool())

        and: "A Person Message"
        def personMessage = new PersonMessage(new Person(), PersonMessage.ACTION.CREATE)

        and: "A blocking variable"
        def vars = new BlockingVariables(2)

        and: "A Fake Service"
        def fakeService = Stub(PersonService)
        fakeService.process >> { PersonMessage message -> vars.message = message}

        and: "A subscriber"
        def subscriber = new PersonSubscriber(eventBus, fakeService)

        when:
        eventBus.post(personMessage)

        then:
        vars.message == personMessage
    }
}
