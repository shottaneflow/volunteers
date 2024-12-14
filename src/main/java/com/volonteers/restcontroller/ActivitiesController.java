package com.volonteers.restcontroller;

import com.volonteers.model.Activity;
import com.volonteers.model.Event;
import com.volonteers.service.ActivityService;
import com.volonteers.service.EventService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/events-api/{id}")
public class ActivitiesController {

    private final EventService eventService;
    private final ActivityService activityService;

    public ActivitiesController(EventService eventService, ActivityService activityService) {
        this.eventService = eventService;
        this.activityService = activityService;
    }
    @GetMapping("/activities")
    public Iterable<Activity> getActivities(@PathVariable int id) {
        return this.eventService.getActivitiesByEventId(id);
    }
    @GetMapping("/activities/sort/asc")
    public List<Activity> getSortedActivitiesAsc(@PathVariable int id) {
        return activityService.sortActivitiesByDateTimeAsc(id);
    }

    @GetMapping("/activities/sort/desc")
    public List<Activity> getSortedActivitiesDesc(@PathVariable int id) {
        return activityService.sortActivitiesByDateTimeDesc(id);
    }
}
