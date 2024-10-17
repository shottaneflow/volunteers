package com.volonteers.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
public class Activity {
    @Id
    private int id;
    @Column(name="c_name")
    private String name;
    @Column(name="c_start_date")
    private LocalDate startDate;
    @Column(name="c_end_date")
    private LocalDate endDate;
    @Column(name = "c_required_volunteers")
    @Size(min = 0, max = 250)
    private Integer requiredVolunteers;
    @Column(name = "c_registered_volunteers")
    @Size(min = 0, max = 250)
    private Integer registeredVolunteers;
    @ManyToMany
    @JoinTable(name="t_activity_locations",
    joinColumns = @JoinColumn(name = "id_activity"),
    inverseJoinColumns = @JoinColumn(name = "id_location"))
    private List<Location> locations;

    @ManyToMany
    @JoinTable(name="t_activity_languages",
    joinColumns = @JoinColumn(name="id_activity"),
    inverseJoinColumns = @JoinColumn(name="id_language"))
    private List <Language> languages;

    public Activity() {
        locations = new ArrayList<>();
        languages=new ArrayList<>();
    }
}
