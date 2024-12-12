package com.volonteers.restcontroller;


import com.volonteers.model.Activity;
import com.volonteers.model.Event;
import com.volonteers.service.ActivityService;
import com.volonteers.service.EventService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

@RestController
@RequestMapping("/admin/events-api")
public class AdminEventsController {

    private final EventService eventService;
    private final ActivityService activityService;

    public AdminEventsController(EventService eventService, ActivityService activityService ) {
        this.eventService = eventService;
        this.activityService = activityService;
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
        List<Activity> activities = new ArrayList<>();
        this.eventService.getActivitiesByEventId(id).forEach(activities::add);

        // Удаляем активности
        for (Activity activity : activities) {
            this.eventService.deleteActivityByEventId(id, activity);
            this.activityService.deleteActivityById(activity.getId());
        }

        // Удаляем само событие
        eventService.deleteEventById(id);

        return ResponseEntity.noContent().build();
    }
}