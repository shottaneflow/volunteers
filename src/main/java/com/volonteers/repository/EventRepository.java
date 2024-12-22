package com.volonteers.repository;

import com.volonteers.model.Event;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

public interface EventRepository extends CrudRepository<Event, Integer> {
    Iterable<Event> findAllByType(String type);
    Iterable<Event> findAllByStatus(String status);
    @Query("SELECT e FROM Event e JOIN e.activities a WHERE a.id = :activityId")
    Event findByActivityId(@Param("activityId") Integer activityId);
}
