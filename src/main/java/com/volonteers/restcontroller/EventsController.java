package com.volonteers.restcontroller;


import com.volonteers.model.Event;
import com.volonteers.service.EventService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
    @PostMapping("/create-event")
    public ResponseEntity<?> createEvent(@Valid @RequestBody Event event) {
        this.eventService.addEvent(event);
        return ResponseEntity.ok().build();
    }
}
