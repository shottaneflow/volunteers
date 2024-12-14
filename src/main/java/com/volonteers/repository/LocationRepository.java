package com.volonteers.repository;

import com.volonteers.model.Location;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface LocationRepository extends JpaRepository<Location, Integer> {
    List<Location> findAllByNameIn(List<String> names);
}
