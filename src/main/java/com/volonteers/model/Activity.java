package com.volonteers.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Table(name="t_activity")
@Setter
public class Activity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(name="c_name")
    private String name;
    @Column(name="c_start_date")
    private LocalDate startDate;
    @Column(name = "c_required_volunteers")
    @Min(value = 0, message = "Значение должно быть больше или равно 1")
    @Max(value = 250, message = "Значение не должно превышать 1000")
    private Integer requiredVolunteers;
    @Column(name = "c_registered_volunteers")
    @Min(value = 0, message = "Значение должно быть больше или равно 1")
    @Max(value = 250, message = "Значение не должно превышать 1000")
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

    @ManyToMany
    @JoinTable(name="t_activity_users",
    joinColumns = @JoinColumn(name="id_activity"),
    inverseJoinColumns = @JoinColumn(name="id_user"))
    private List <Volunteer> volunteers;

    public Activity() {
        locations = new ArrayList<>();
        languages=new ArrayList<>();
        volunteers=new ArrayList<>();
    }
}
