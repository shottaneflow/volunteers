package com.volonteers.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@Table(name = "t_event")
public class Event {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(name = "c_name")
    @Size(min = 1, max = 150)
    private String name;
    @Column(name="c_date_time")
    private LocalDateTime dateTime;
    @Column(name = "c_type")
    private String type;

    @Column(name = "c_required_volunteers")
    @Size(min = 0, max = 250)
    private Integer requiredVolunteers;
    @Column(name = "c_registered_volunteers")
    @Size(min = 0, max = 250)
    private Integer registeredVolunteers;
    @Column(name = "c_status")
    private String status;
    @ManyToMany
    @JoinTable(name = "t_event_activity",
    joinColumns = @JoinColumn(name="id_event"),
    inverseJoinColumns = @JoinColumn(name="id_activity"))
    private List<Activity> activities;

    public Event() {
        this.activities=new ArrayList<>();
    }


}
