package com.volonteers.service;


import com.volonteers.model.Activity;
import com.volonteers.model.Event;
import com.volonteers.repository.EventRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

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
    @Override
    public void deleteEventById(int id) {
        this.eventRepository.deleteById(id);
    }
    @Override
    public List<Event> sortEventsByDateTimeAsc() {
        Iterable<Event> eventsIterable = eventRepository.findAll();
        List<Event> events = new ArrayList<>();
        eventsIterable.forEach(events::add);
        events.sort((event1, event2) -> event1.getDateTime().compareTo(event2.getDateTime())); // Сортировка по возрастанию
        return events;
    }

    @Override
    public List<Event> sortEventsByDateTimeDesc() {
        Iterable<Event> eventsIterable = eventRepository.findAll();
        List<Event> events = new ArrayList<>();
        eventsIterable.forEach(events::add);
        events.sort((event1, event2) -> event2.getDateTime().compareTo(event1.getDateTime())); // Сортировка по убыванию
        return events;
    }

    @Override
    public Iterable<Activity> getActivitiesByEventId(int id){
        return this.eventRepository.findById(id).get().getActivities();
    }

    @Override
    @Transactional
    public void addActivityByEventId(int id, Activity activity) {
        this.eventRepository.findById(id).get().getActivities().add(activity);
    }

    @Override
    public void deleteActivityByEventId(int eventId,Activity activity) {
        this.eventRepository.findById(eventId).get().getActivities().remove(activity);
    }
}
