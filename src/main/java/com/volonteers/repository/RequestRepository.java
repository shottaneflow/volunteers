package com.volonteers.repository;

import com.volonteers.model.Request;
import org.springframework.data.repository.CrudRepository;

public interface RequestRepository  extends CrudRepository<Request, Integer> {
    Iterable<Request> findAllByUserId(int userId);
}
