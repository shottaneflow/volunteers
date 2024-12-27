package com.volonteers.repository;

import com.volonteers.model.Activity;
import org.springframework.data.repository.CrudRepository;

public interface ActivityRepository extends CrudRepository<Activity, Integer> {
    Activity findByName(String name);
}
