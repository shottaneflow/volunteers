package com.volonteers.service;

import com.volonteers.model.Activity;
import com.volonteers.model.Event;
import com.volonteers.model.Volunteer;
import com.volonteers.repository.ActivityRepository;
import com.volonteers.repository.EventRepository;
import com.volonteers.repository.LocationRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;


@Service
public class DefaultActivityService  implements ActivityService {

    private final ActivityRepository activityRepository;
    private final EventRepository eventRepository;


    public DefaultActivityService(ActivityRepository activityRepository, EventRepository eventRepository) {
        this.activityRepository = activityRepository;
        this.eventRepository = eventRepository;
    }

    @Override
    public void addActivity(Activity activity) {
        this.activityRepository.save(activity);
    }
    @Override
    public void deleteActivityById(int id) {
        this.activityRepository.deleteById(id);
    }
    @Override
    public Activity getActivityById(int id) {
        return this.activityRepository.findById(id).orElse(null);
    }
    @Override
    public void save(Activity activity) {
        activityRepository.save(activity);
    }
    @Override
    public List<Activity> sortActivitiesByDateTimeAsc(int id) {
        Event event = this.eventRepository.findById(id).orElse(null);
        List<Activity> activities = event.getActivities();
        activities.sort((activity1, activity2) -> activity1.getDateTime().compareTo(activity2.getDateTime())); // Сортировка по возрастанию
        return activities;
    }
    @Override
    public List<Activity> sortActivitiesByDateTimeDesc(int id) {
        Event event = this.eventRepository.findById(id).orElse(null);
        List<Activity> activities = event.getActivities();
        activities.sort((activity1, activity2) -> activity1.getDateTime().compareTo(activity1.getDateTime())); // Сортировка по убыванию
        return activities;
    }
    @Override
    public void addVolunteer(int activityId,Volunteer volunteer) {
        Activity activity = this.activityRepository.findById(activityId).orElse(null);
        activity.getVolunteers().add(volunteer);
        activity.setRegisteredVolunteers(activity.getRegisteredVolunteers()+1);
        Event event = this.eventRepository.findByActivityId(activityId);
        if(event.getRegisteredVolunteers()==null)
            event.setRegisteredVolunteers(0);
        event.setRegisteredVolunteers(event.getRegisteredVolunteers()+1);
        this.eventRepository.save(event);
        this.activityRepository.save(activity);
    }
    @Override
    public Activity getActivityByName(String name) {
        return this.activityRepository.findByName(name);
    }


}
