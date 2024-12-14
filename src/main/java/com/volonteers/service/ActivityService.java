package com.volonteers.service;

import com.volonteers.model.Activity;

public interface ActivityService {
    void addActivity(Activity activity);
    void deleteActivityById(int id);
    Activity getActivityById(int id);
    void save(Activity activity);
}
