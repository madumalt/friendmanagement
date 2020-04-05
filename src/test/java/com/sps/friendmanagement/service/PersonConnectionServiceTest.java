package com.sps.friendmanagement.service;

import com.sps.friendmanagement.model.Person;
import com.sps.friendmanagement.model.PersonConnection;
import com.sps.friendmanagement.repository.PersonConnectionRepository;
import com.sps.friendmanagement.util.FriendManagementUtil;
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
@SpringBootTest(classes = PersonConnection.class)
public class PersonConnectionServiceTest {
    @Autowired
    private PersonConnectionService personConnectionService;

    @MockBean
    private PersonService personService;
    @MockBean
    private PersonConnectionRepository personConnectionRepository;
    @Autowired
    private FriendManagementUtil friendManagementUtil;

    private String personEmail;
    private String friendEmail;
    private Person person;
    private Person friend;
    private PersonConnection connection;

    @Before
    public void prepare() {
        personEmail = "a@gmail.com";
        friendEmail = "b@gmail.com";
        person = Person.builder().email(personEmail).build();
        friend = Person.builder().email(friendEmail).build();
        connection = PersonConnection.builder().person(person).friend(friend).build();
    }

    @Test
    public void blockFriend() {
        PersonConnection blockedConnection = PersonConnection.builder()
                .person(person)
                .friend(friend)
                .isBlocked(true)
                .build();

        when(personConnectionRepository.findByPersonAndFriendEmail(personEmail, friendEmail)).thenReturn(connection);
        when(personConnectionRepository.save(connection)).thenReturn(blockedConnection);

        PersonConnection result = personConnectionService.blockFriend(personEmail, friendEmail);

        assertThat(result).isNotNull();
        assertThat(result.isBlocked()).isTrue();
    }
}
