package com.volonteers.service;

import com.volonteers.model.Event;

import java.util.List;

public interface EventService {
    Iterable<Event> getEvents();
    void addEvent(Event event);
    void deleteEventById(int id);
    public List<Event> sortEventsByDateTimeAsc();
    public List<Event> sortEventsByDateTimeDesc();
}
