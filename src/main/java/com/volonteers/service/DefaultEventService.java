package com.volonteers.service;


import com.volonteers.model.Event;
import com.volonteers.repository.EventRepository;
import org.springframework.stereotype.Service;

@Service
public class DefaultEventService implements EventService {

    private final EventRepository eventRepository;

    public DefaultEventService(EventRepository eventRepository) {
        this.eventRepository = eventRepository;
    }
    @Override
    public Iterable<Event> getEvents() {
        return this.eventRepository.findAll();
    }

    @Override
    public void addEvent(Event event) {
        this.eventRepository.save(event);
    }
}
