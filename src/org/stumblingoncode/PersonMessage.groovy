package org.stumblingoncode

class PersonMessage {
    private final Person person
    private final ACTION action

    enum ACTION {
        CREATE, UPDATE, DELETE
    }

    PersonMessage(Person person, PersonMessage.ACTION action) {
        this.person = person
        this.action = action
    }

    Person getPerson() {
        person
    }

    ACTION getAction() {
        action
    }
}
