package org.stumblingoncode

import com.google.common.eventbus.AllowConcurrentEvents
import com.google.common.eventbus.AsyncEventBus
import com.google.common.eventbus.Subscribe

class PersonSubscriber implements Subscriber {
    PersonService personService

    PersonSubscriber(AsyncEventBus eventBus, PersonService personService) {
        this.personService = personService
        eventBus.register(this)
    }

    @Override
    @Subscribe
    @AllowConcurrentEvents
    void handleMessage(PersonMessage message) {
        personService.process(message)
    }
}
