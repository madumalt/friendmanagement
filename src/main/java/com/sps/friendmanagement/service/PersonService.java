package com.sps.friendmanagement.service;

import com.sps.friendmanagement.model.Person;
import com.sps.friendmanagement.repository.PersonRepository;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class PersonService {
    private final PersonRepository personRepository;

    public PersonService(PersonRepository personRepository){
        this.personRepository = personRepository;
    }

    public Person save(Person person) {
        return personRepository.save(person);
    }

    public List<Person> saveAll(List<Person> people){
        return personRepository.saveAll(people).stream().collect(Collectors.toList());
    }

    public Person findByEmail(String email) {
        return personRepository.findByEmail(email);
    }
}
