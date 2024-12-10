package com.volonteers.restcontroller;


import com.volonteers.model.Event;
import com.volonteers.service.EventService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/admin/events-api")
public class AdminEventsController {

    private final EventService eventService;

    public AdminEventsController(EventService eventService) {
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
    @DeleteMapping("/events/{id}")
    public ResponseEntity<Void> deleteEvent(@PathVariable int id) {
        eventService.deleteEventById(id);
        return ResponseEntity.noContent().build(); // Возвращаем 204 No Content
    }
}
