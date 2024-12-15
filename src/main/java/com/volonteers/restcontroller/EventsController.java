package com.volonteers.restcontroller;


import com.volonteers.model.Event;
import com.volonteers.service.EventService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping("/events-api")
public class EventsController {

    private final EventService eventService;


    public EventsController(EventService eventService) {
        this.eventService = eventService;
    }

    @GetMapping("/all-events")
    public Iterable<Event> getEvents() {
        return this.eventService.getEvents();
    }
    @GetMapping("/events/sort/asc")
    public List<Event> getSortedEventsAsc() {
        return eventService.sortEventsByDateTimeAsc();
    }

    @GetMapping("/events/sort/desc")
    public List<Event> getSortedEventsDesc() {
        return eventService.sortEventsByDateTimeDesc();
    }
    @GetMapping("/events/filter/type")
    public List<Event> getFilteredEventsType(@RequestParam List<String> types) {
        List<Event> filteredEvents = new ArrayList<>();
        for (String type : types) {
            filteredEvents.addAll(this.eventService.getEventsFilterByType(type));
        }
        return filteredEvents;
    }


    @GetMapping("/events/filter/status")
    public List<Event> getFilteredEventsStatus(@RequestParam List<String> status) {
        List<Event> filteredEvents = new ArrayList<>();
        for (String s : status) {
            filteredEvents.addAll(this.eventService.getEventsFilterByStatus(s));
        }
        return filteredEvents;
    }



}
