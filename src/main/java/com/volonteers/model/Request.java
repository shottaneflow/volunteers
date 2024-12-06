package com.volonteers.model;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "t_request")
public class Request {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    @JoinColumn(name="user_id", nullable=false)
    private Volunteer user;

    @ManyToOne
    @JoinColumn(name="activity_id",nullable = false)
    private Activity activity;

    @Column(name="c_status")
    private String status;
}
