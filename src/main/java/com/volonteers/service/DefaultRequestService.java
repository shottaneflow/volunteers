package com.volonteers.service;

import com.volonteers.model.Request;
import com.volonteers.model.Volunteer;
import com.volonteers.repository.RequestRepository;
import org.springframework.stereotype.Service;

@Service
public class DefaultRequestService implements RequestService {

    private final RequestRepository requestRepository;
    private  final ActivityService activityService;

    public DefaultRequestService(RequestRepository requestRepository,
                                 ActivityService activityService) {
        this.requestRepository = requestRepository;
        this.activityService = activityService;
    }

   @Override
    public void newRequest(int activityId, Volunteer volunteer) {
        Request request = new Request();
        request.setActivity(activityService.getActivityById(activityId));
        request.setUser(volunteer);
        request.setStatus("На рассмотрении");
        requestRepository.save(request);
   }
   @Override
    public Iterable<Request> findRequestsByUserId(int userId) {
        return this.requestRepository.findAllByUserId(userId);
   }
   @Override
    public Iterable<Request> findAll(){
        return this.requestRepository.findAll();
   }
   @Override
    public  Request findById(int requestId) {
        return this.requestRepository.findById(requestId).get();
   }
   @Override
    public void save(Request request) {
        this.requestRepository.save(request);
   }
}
