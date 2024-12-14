package com.volonteers.restcontroller;

import com.volonteers.model.Request;
import com.volonteers.model.Volunteer;
import com.volonteers.service.RequestService;
import com.volonteers.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@RequestMapping("/request-api")
public class RequestController {

    private final UserService userService;
    private final RequestService requestService;

    public RequestController(UserService userService, RequestService requestService) {
        this.userService = userService;
        this.requestService = requestService;
    }


    @PostMapping("/add-request/{activityId}")
    public ResponseEntity<?> addRequest(@PathVariable("activityId") int activityId, Principal principal) {
        Volunteer volunteer =this.userService.findByUsername(principal.getName());
        this.requestService.newRequest(activityId,volunteer);
        return ResponseEntity.ok().build();

    }
    @GetMapping("/my-requests")
    public Iterable<Request> getMyRequests(Principal principal) {
        Volunteer volunteer =this.userService.findByUsername(principal.getName());
        return this.requestService.findRequestsByUserId(volunteer.getId());
    }
}
