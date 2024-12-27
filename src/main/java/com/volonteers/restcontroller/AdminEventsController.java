package com.volonteers.restcontroller;


import com.volonteers.model.Activity;
import com.volonteers.model.Event;
import com.volonteers.service.ActivityService;
import com.volonteers.service.EventService;
import com.volonteers.service.RequestService;
import com.volonteers.service.UserService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
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
    private final RequestService requestService;

    public AdminEventsController(EventService eventService, ActivityService activityService, RequestService requestService ) {
        this.eventService = eventService;
        this.activityService = activityService;
        this.requestService = requestService;
    }

    @GetMapping("/all-events")
    public Iterable<Event> getEvents() {
        return this.eventService.getEvents();
    }

    @PostMapping("/create-event")
    public ResponseEntity<?> createEvent(@Valid @RequestBody Event event) {
        if(this.eventService.getEventByName(event.getName()) != null) {
            return new ResponseEntity<>("Событие "+event.getName()+" уже существует", HttpStatus.BAD_REQUEST);
        }
        this.eventService.addEvent(event);
        return ResponseEntity.ok().build();
    }
    @DeleteMapping("/events/{id}")
    public ResponseEntity<Void> deleteEvent(@PathVariable int id) {
        List<Activity> activities = new ArrayList<>();
        this.eventService.getActivitiesByEventId(id).forEach(activities::add);
        for (Activity activity : activities) {
            this.requestService.deleteByActivityId(activity.getId());
            this.eventService.deleteActivityByEventId(id, activity);
            this.activityService.deleteActivityById(activity.getId());
        }
        eventService.deleteEventById(id);
        return ResponseEntity.noContent().build();
    }
    @PostMapping("/{id}/edit-event")
    public ResponseEntity<?> editEvent(@PathVariable int id,@Valid @RequestBody Event event) {
        if(this.eventService.getEventByName(event.getName()) != null) {
            return new ResponseEntity<>("Событие "+event.getName()+" уже существует", HttpStatus.BAD_REQUEST);
        }
        this.eventService.editEventById(id,event);
        return ResponseEntity.ok().build();
    }
    @GetMapping("/events/{id}")
    public ResponseEntity<Event> getEventById(@PathVariable int id) {
        Event event = eventService.getEventById(id);
        return ResponseEntity.ok(event);
    }

}
