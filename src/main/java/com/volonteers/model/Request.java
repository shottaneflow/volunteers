package com.volonteers.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
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

    @ManyToOne(fetch = FetchType.EAGER) // Связь с таблицей t_user
    @JoinColumn(name = "user_id", nullable = false, foreignKey = @ForeignKey(name = "fk_request_user"))

    private Volunteer user;

    @ManyToOne(fetch = FetchType.EAGER) // Связь с таблицей t_activity
    @JoinColumn(name = "activity_id", nullable = false, foreignKey = @ForeignKey(name = "fk_request_activity"))

    private Activity activity;

    @Column(name = "c_status", nullable = false, length = 30)
    private String status;
}
