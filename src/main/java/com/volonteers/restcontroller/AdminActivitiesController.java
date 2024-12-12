package com.volonteers.restcontroller;


import com.volonteers.model.Activity;
import com.volonteers.service.ActivityService;
import com.volonteers.service.EventService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/admin/events-api/{id}")
public class AdminActivitiesController {

    private final ActivityService activityService;

    private final EventService eventService;

    public AdminActivitiesController(ActivityService activityService, EventService eventService) {
        this.activityService = activityService;
        this.eventService = eventService;
    }

    @GetMapping("/activities")
    public Iterable<Activity> getActivities(@PathVariable int id) {
        return this.eventService.getActivitiesByEventId(id);
    }
    @PostMapping("/create-activity")
    public void createActivity(@PathVariable int id, @RequestBody Activity activity) {
        this.activityService.addActivity(activity);
        this.eventService.addActivityByEventId(id,activity);
    }
    @DeleteMapping("/delete-activity/{activityId}")
    public void deleteActivity(@PathVariable int activityId, @PathVariable int id) {
        this.eventService.deleteActivityByEventId(id,this.activityService.getActivityById(activityId));
        this.activityService.deleteActivityById(activityId);

    }
}
