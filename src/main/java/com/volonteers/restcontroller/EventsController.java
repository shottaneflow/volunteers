package com.volonteers.restcontroller;


import com.volonteers.model.Event;
import com.volonteers.service.EventService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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


}
