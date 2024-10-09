package com.volonteers.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name="t_user")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "c_username")
    private String username;
    @Column(name = "c_password")
    private String password;
    @Column(name="c_email")
    private String email;
    @Column(name="c_fio")
    private String fio;
    @Column(name = "c_date_of_birth")
    private String dateOfBirth;
    @Column(name = "c_gender")
    private String gender;



    @ManyToMany
    @JoinTable(name="t_user_authority",
    joinColumns = @JoinColumn(name="id_user"),
    inverseJoinColumns = @JoinColumn(name="id_authority"))
    private List <Authority> authorities;

    @ManyToMany
    @JoinTable(name="t_user_languages",
    joinColumns =@JoinColumn(name = "id_user"),
    inverseJoinColumns = @JoinColumn(name = "id_language"))
    private List <Language> languages;



    public User() {
        this.authorities=new ArrayList<>();
    }
}
