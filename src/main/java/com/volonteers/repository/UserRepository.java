package com.volonteers.repository;

import com.volonteers.model.Volunteer;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface UserRepository extends CrudRepository<Volunteer, Integer> {
    Optional<Volunteer> findByUsername(String username);
    Volunteer findByActivationCode(String activationCode);

}
