package com.volonteers.service;

import com.volonteers.model.Event;

public interface EventService {
    Iterable<Event> getEvents();
    void addEvent(Event event);
}
