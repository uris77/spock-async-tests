package org.stumblingoncode

import com.google.common.eventbus.AsyncEventBus


class PersonPublisher implements Publisher {
    private final AsyncEventBus eventBus

    PersonPublisher(AsyncEventBus eventBus) {
        this.eventBus = eventBus
    }

    @Override
    void notifyChange(PersonMessage message) {
        eventBus.post(message)
    }
}
