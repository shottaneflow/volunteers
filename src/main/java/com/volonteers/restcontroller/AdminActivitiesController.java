package com.volonteers.restcontroller;


import com.volonteers.model.Activity;
import com.volonteers.model.Event;
import com.volonteers.model.Location;
import com.volonteers.service.ActivityService;
import com.volonteers.service.EventService;
import com.volonteers.service.LocationService;
import com.volonteers.service.RequestService;
import org.apache.coyote.BadRequestException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin/events-api/{id}")
public class AdminActivitiesController {

    private final ActivityService activityService;

    private final EventService eventService;

    private final LocationService locationService;

    private final RequestService requestService;

    public AdminActivitiesController(ActivityService activityService, EventService eventService
    , LocationService locationService, RequestService requestService) {
        this.activityService = activityService;
        this.eventService = eventService;
        this.locationService = locationService;
        this.requestService = requestService;
    }

    @GetMapping("/activities")
    public Iterable<Activity> getActivities(@PathVariable int id) {
        return this.eventService.getActivitiesByEventId(id);
    }
    @PostMapping("/create-activity")
    public ResponseEntity<?> createActivity(@PathVariable int id, @RequestBody Activity activity) throws BadRequestException {
        if(this.activityService.getActivityByName(activity.getName()) != null) {
            return new ResponseEntity<>("Мероприятие " + activity.getName() + " уже существует", HttpStatus.BAD_REQUEST);
        }
        List<Location> updatedLocations = this.locationService.saveLocations(activity.getLocations());
        activity.setLocations(updatedLocations);
        this.activityService.addActivity(activity);
        this.eventService.addActivityByEventId(id,activity);
        return ResponseEntity.ok().build();
    }
    @DeleteMapping("/delete-activity/{activityId}")
    public void deleteActivity(@PathVariable int activityId, @PathVariable int id) {
        this.requestService.deleteByActivityId(activityId);
        Event event = this.eventService.getEventById(id);
        Activity activity = this.activityService.getActivityById(activityId);
        event.setRegisteredVolunteers(event.getRegisteredVolunteers()-activity.getRegisteredVolunteers());
        this.eventService.addEvent(event);
        this.eventService.deleteActivityByEventId(id,this.activityService.getActivityById(activityId));
        this.activityService.deleteActivityById(activityId);
    }
    @PostMapping("/edit-activity/{activityId}")
    public ResponseEntity<?> editActivity(@PathVariable int id, @PathVariable int activityId, @RequestBody Activity activity) throws BadRequestException {
        if(this.activityService.getActivityByName(activity.getName()) != null) {
            return new ResponseEntity<>("Мероприятие " + activity.getName() + " уже существует", HttpStatus.BAD_REQUEST);
        }
        Activity existingActivity = activityService.getActivityById(activityId);
        existingActivity.setName(activity.getName());
        existingActivity.setStartDate(activity.getStartDate());
        existingActivity.setRequiredVolunteers(activity.getRequiredVolunteers());
        existingActivity.setRegisteredVolunteers(activity.getRegisteredVolunteers());

        List<Location> updatedLocations = this.locationService.saveLocations(activity.getLocations());
        existingActivity.setLocations(updatedLocations);
        existingActivity.setLanguages(activity.getLanguages());
        existingActivity.setVolunteers(activity.getVolunteers());
        activityService.save(existingActivity);
        return ResponseEntity.ok("Activity updated successfully");
    }
    @GetMapping("/activity/{activityId}")
    public ResponseEntity<Activity> getActivity(@PathVariable int activityId) {
        Activity activity = this.activityService.getActivityById(activityId);
        return ResponseEntity.ok().body(activity);
    }
}
