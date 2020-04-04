package com.sps.friendmanagement.repository;

import com.sps.friendmanagement.model.Person;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface PersonRepository extends JpaRepository<Person, Long> {
    List<Person> findAll();

    Person findByEmail(String email);
}
