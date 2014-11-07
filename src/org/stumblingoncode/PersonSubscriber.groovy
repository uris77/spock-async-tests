package org.stumblingoncode

import com.google.common.eventbus.AsyncEventBus

class PersonSubscriber implements Subscriber {
    PersonService personService

    PersonSubscriber(AsyncEventBus eventBus, PersonService personService) {
        this.personService = personService
        eventBus.register(this)
    }

    @Override
    void handleMessage(PersonMessage message) {
        personService.process(message)
    }
}
