package com.volonteers.model;

import jakarta.persistence.*;
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
    private String name;
    @Column(name="c_date_time")
    private LocalDateTime dateTime;
    @Column(name = "c_type")
    private String type;

    @Column(name = "c_required_volunteers")
    private Integer requiredVolunteers;
    @Column(name = "c_registered_volunteers")
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
