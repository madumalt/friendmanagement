package com.sps.friendmanagement.service;

import com.sps.friendmanagement.model.Person;
import com.sps.friendmanagement.repository.PersonRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = PersonService.class)
public class PersonServiceTest {

    @Autowired
    private PersonService personService;

    @MockBean
    private PersonRepository personRepository;

    private Person person_obj, person_db;
    private String email;

    @Before
    public void prepare() {
        email = "test@email.com";
        person_obj = Person.builder().email(email).build();
        person_db = Person.builder().email(email).id(1L).build();
    }

    @Test
    public void save() {
        when(personRepository.save(person_obj)).thenReturn(person_db);

        Person result = personService.save(person_obj);
        assertThat(result).isNotNull();
        assertThat(result.getEmail()).isEqualTo(email);
    }

    @Test
    public void findByEmail() {
        when(personRepository.findByEmail(email)).thenReturn(person_db);

        Person result = personService.findByEmail(email);
        assertThat(result).isNotNull();
        assertThat(result.getEmail()).isEqualTo(email);
    }

    @Test
    public void findByEmailNotExist() {
        String doesntExistPersonEmail = "doesnt@exist.email";
        when(personRepository.findByEmail(doesntExistPersonEmail)).thenReturn(null);

        Person result = personService.findByEmail(doesntExistPersonEmail);
        assertThat(result).isNull();
    }
}
