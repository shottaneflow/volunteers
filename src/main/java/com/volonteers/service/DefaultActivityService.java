package com.volonteers.service;

import com.volonteers.model.Activity;
import com.volonteers.repository.ActivityRepository;
import org.springframework.stereotype.Service;


@Service
public class DefaultActivityService  implements ActivityService {

    private final ActivityRepository activityRepository;

    public DefaultActivityService(ActivityRepository activityRepository) {
        this.activityRepository = activityRepository;
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
}
