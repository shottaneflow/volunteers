package com.volonteers.service;

import com.volonteers.model.Activity;
import com.volonteers.model.Event;

import java.util.List;

public interface EventService {
    Iterable<Event> getEvents();
    void addEvent(Event event);
    void deleteEventById(int id);
    public List<Event> sortEventsByDateTimeAsc();
    public List<Event> sortEventsByDateTimeDesc();
    Iterable<Activity> getActivitiesByEventId(int eventId);
    void addActivityByEventId(int eventId, Activity activity);
    void deleteActivityByEventId(int eventId,Activity activity);
    void editEventById(int eventId, Event event);
    Event getEventById(int eventId);
    List<Event> getEventsFilterByType(String type);
    List<Event> getEventsFilterByStatus(String status);
    Event getEventByName(String name);
}
