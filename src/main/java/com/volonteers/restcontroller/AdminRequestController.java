package com.volonteers.restcontroller;

import com.volonteers.model.Request;
import com.volonteers.model.Volunteer;
import com.volonteers.service.ActivityService;
import com.volonteers.service.RequestService;
import com.volonteers.service.UserService;
import org.apache.coyote.Response;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/admin/request-api")
public class AdminRequestController {

    private final RequestService requestService;
    private final UserService userService;
    private final ActivityService activityService;

    public AdminRequestController(RequestService requestService,
                                  UserService userService,
                                  ActivityService activityService) {
        this.requestService = requestService;
        this.userService = userService;
        this.activityService = activityService;
    }
    @GetMapping("/all-requests")
    public ResponseEntity<?> getAllRequests() {
        Iterable<Request> requests=this.requestService.findAll();
        return ResponseEntity.ok(requests);
    }
    @PostMapping("/accept/{id}")
    public ResponseEntity<?> agree(@PathVariable int id) {
        Request request= this.requestService.findById(id);
        request.setStatus("Одобрено");
        Volunteer volunteer=this.userService.findByUsername(request.getUser().getUsername());
        this.activityService.addVolunteer(request.getActivity().getId(),volunteer);
        this.requestService.save(request);
        return ResponseEntity.ok().build();
    }
    @PostMapping("/reject/{id}")
    public ResponseEntity<?> reject(@PathVariable int id) {
        Request request= this.requestService.findById(id);
        request.setStatus("Отклоненно");
        this.requestService.save(request);
        return ResponseEntity.ok().build();

    }
}
