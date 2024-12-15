package com.volonteers.repository;

import com.volonteers.model.Event;
import org.springframework.data.repository.CrudRepository;

public interface EventRepository extends CrudRepository<Event, Integer> {
    Iterable<Event> findAllByType(String type);
    Iterable<Event> findAllByStatus(String status);
}
