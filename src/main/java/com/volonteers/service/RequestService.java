package com.volonteers.service;


import com.volonteers.model.Request;
import com.volonteers.model.Volunteer;

public interface RequestService {
    void newRequest(int activityId, Volunteer volunteer);
    Iterable<Request> findRequestsByUserId(int userId);
    Iterable<Request> findAll();
    Request findById(int requestId);
    void save(Request request);
    void deleteByActivityId(int activityId);
}
