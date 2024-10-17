package com.volonteers.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
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
    @Size(min=4,max=8)
    private String username;
    @Column(name = "c_password")
    @Size(min=4,max=12)
    private String password;
    @Column(name="c_email")
    @Size(min=5,max=25)
    private String email;
    @Column(name="c_fio")
    @Size(min=5,max=50)
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
