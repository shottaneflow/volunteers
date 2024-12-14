package com.volonteers.service;

import com.volonteers.model.Activity;
import com.volonteers.model.Event;
import com.volonteers.model.Volunteer;

import java.util.List;

public interface ActivityService {
    void addActivity(Activity activity);
    void deleteActivityById(int id);
    Activity getActivityById(int id);
    void save(Activity activity);
    List<Activity>  sortActivitiesByDateTimeAsc(int id);
    List<Activity> sortActivitiesByDateTimeDesc(int id);
    void addVolunteer(int activityId,Volunteer volunteer);
}
