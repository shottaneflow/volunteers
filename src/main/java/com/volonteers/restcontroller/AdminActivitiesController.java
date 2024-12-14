package com.volonteers.restcontroller;


import com.volonteers.model.Activity;
import com.volonteers.model.Location;
import com.volonteers.service.ActivityService;
import com.volonteers.service.EventService;
import com.volonteers.service.LocationService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin/events-api/{id}")
public class AdminActivitiesController {

    private final ActivityService activityService;

    private final EventService eventService;

    private final LocationService locationService;

    public AdminActivitiesController(ActivityService activityService, EventService eventService
    , LocationService locationService) {
        this.activityService = activityService;
        this.eventService = eventService;
        this.locationService = locationService;
    }

    @GetMapping("/activities")
    public Iterable<Activity> getActivities(@PathVariable int id) {
        return this.eventService.getActivitiesByEventId(id);
    }
    @PostMapping("/create-activity")
    public void createActivity(@PathVariable int id, @RequestBody Activity activity) {
        List<Location> updatedLocations = this.locationService.saveLocations(activity.getLocations());
        activity.setLocations(updatedLocations);
        this.activityService.addActivity(activity);
        this.eventService.addActivityByEventId(id,activity);
    }
    @DeleteMapping("/delete-activity/{activityId}")
    public void deleteActivity(@PathVariable int activityId, @PathVariable int id) {
        this.eventService.deleteActivityByEventId(id,this.activityService.getActivityById(activityId));
        this.activityService.deleteActivityById(activityId);
    }
    @PostMapping("/edit-activity/{activityId}")
    public ResponseEntity<?> editActivity(@PathVariable int id, @PathVariable int activityId, @RequestBody Activity activity) {
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
