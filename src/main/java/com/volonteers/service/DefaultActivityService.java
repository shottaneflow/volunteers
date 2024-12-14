package com.volonteers.service;

import com.volonteers.model.Activity;
import com.volonteers.repository.ActivityRepository;
import com.volonteers.repository.LocationRepository;
import org.springframework.stereotype.Service;


@Service
public class DefaultActivityService  implements ActivityService {

    private final ActivityRepository activityRepository;
    private final LocationService locationService;

    public DefaultActivityService(ActivityRepository activityRepository, LocationService locationService) {
        this.activityRepository = activityRepository;
        this.locationService = locationService;
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

}
