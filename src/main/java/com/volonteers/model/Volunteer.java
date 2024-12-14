package com.volonteers.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@Entity
@Table(name="t_user")
public class Volunteer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "c_username")
    @Size(min=4,max=8)
    private String username;
    @Column(name = "c_password")
    private String password;
    @Column(name="c_email")
    @Size(min=5,max=50)
    private String email;
    @Column(name="c_fio")
    @Size(min=5,max=50)
    private String fio;
    @Column(name = "c_date_of_birth")
    private LocalDate dateOfBirth;
    @Column(name = "c_gender")
    private String gender;

    @Column(name="c_about")
    @Size(min=0,max=500)
    private String about;

    @Column(name = "c_activation_code")
    private String activationCode;
    @Column(name="c_active")
    private boolean active;



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



    public Volunteer() {
        this.authorities=new ArrayList<>();
    }

    public void addAuthorities(Authority authority) {
        this.authorities.add(authority);
    }





}
