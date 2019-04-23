package com.example.disruptor.single;

import com.lmax.disruptor.EventFactory;

public class PersonEvent {

    private Person person;

    public Person getPerson() {
        return person;
    }

    public void setPerson(Person person) {
        this.person = person;
    }

    public final static EventFactory<PersonEvent> EVENT_FACTORY = new EventFactory<PersonEvent>() {
        public PersonEvent newInstance() {
            return new PersonEvent();
        }
    };
}
