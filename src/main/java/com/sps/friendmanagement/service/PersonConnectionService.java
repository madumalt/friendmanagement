package com.sps.friendmanagement.service;

import com.sps.friendmanagement.model.Person;
import com.sps.friendmanagement.model.PersonConnection;
import com.sps.friendmanagement.repository.PersonConnectionRepository;
import com.sps.friendmanagement.util.FriendManagementUtil;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.transaction.Transactional;
import java.util.*;
import java.util.stream.Collectors;

@Service
@Transactional
public class PersonConnectionService {
    private final PersonService personService;
    private final PersonConnectionRepository personConnectionRepository;
    private final FriendManagementUtil friendManagementUtil;

    public PersonConnectionService(PersonService personService, PersonConnectionRepository personConnectionRepository,
                                   FriendManagementUtil friendManagementUtil) {
        this.personService = personService;
        this.personConnectionRepository = personConnectionRepository;
        this.friendManagementUtil = friendManagementUtil;
    }

    public List<PersonConnection> addFriends(List<Person> friends) {
        Person p1 = getPerson(friends.get(0));
        Person p2 = getPerson(friends.get(1));

        PersonConnection fc1 = PersonConnection.builder().person(p1).friend(p2).isExplicitFriendship(true).build();
        PersonConnection fc2 = PersonConnection.builder().person(p2).friend(p1).isExplicitFriendship(true).build();

        // Add Friends (i.e. save PersonConnetions) and return the saved PersonConnections.
        List<PersonConnection> personConnectionList = new ArrayList<>();
        return personConnectionRepository.saveAll(Arrays.asList(fc1, fc2)).stream().collect(Collectors.toList());
    }

    public List<String> getFriendList(String email) {
        List<PersonConnection> personConnectionList = personConnectionRepository.findByPersonEmail(email);

        if(personConnectionList != null && !personConnectionList.isEmpty()) {
            return personConnectionList.stream()
                    .filter(personConnection -> personConnection.isExplicitFriendship())
                    .map(personConnection -> personConnection.getFriend().getEmail())
                    .collect(Collectors.toList());
        }
        return Collections.emptyList();
    }

    public List<String> getCommonFriends(List<Person> people) {
        Person p1 = getPerson(people.get(0));
        Person p2 = getPerson(people.get(1));

        Set<String> friendsofP1 = getFriendList(p1.getEmail()).stream().collect(Collectors.toSet());
        Set<String> friendsofP2 = getFriendList(p2.getEmail()).stream().collect(Collectors.toSet());

        Set<String> commonFriends = new HashSet<>(friendsofP1);
        commonFriends.retainAll(friendsofP2);
        return commonFriends.stream().collect(Collectors.toList());
    }

    public PersonConnection subscribeToPersonConnection(String personEmail, String friendEmail) {
        return subscribeUnsubscribe(personEmail, friendEmail, true);
    }

    public PersonConnection unsubscribeToPersonConnection(String personEmail, String friendEmail) {
        return subscribeUnsubscribe(personEmail, friendEmail, false);
    }

    public PersonConnection blockFriend(String personEmail, String friendEmail) {
        return blockUnblock(personEmail, friendEmail, true);
    }

    public PersonConnection unBlockFriend(String personEmail, String friendEmail) {
        return blockUnblock(personEmail, friendEmail, false);
    }

    public List<String> getAllEmailsToBeNotified(String email, String text) {

        Set<String> shouldBeNotifiedEmails = new HashSet<>();

        // Retrieves the connections where the given personEmail has non-blocked connection.
        List<PersonConnection> personEmailNotBlockedFriends = personConnectionRepository
                .findByFriendEmailNotBlocked(email);

        if (!CollectionUtils.isEmpty(personEmailNotBlockedFriends)) {
            personEmailNotBlockedFriends.forEach(nonBlockedPersonConnections -> {

                Person person = nonBlockedPersonConnections.getPerson();

                if (nonBlockedPersonConnections.isExplicitFriendship()) {
                    shouldBeNotifiedEmails.add(person.getEmail());
                } else if (nonBlockedPersonConnections.isSubscribed()) {
                    shouldBeNotifiedEmails.add(person.getEmail());
                }
                shouldBeNotifiedEmails.addAll(friendManagementUtil.extractEmails(text));
            });
        }

        return new ArrayList<>(shouldBeNotifiedEmails);
    }

    /**
     * block or unblock the friend.
     * @param personEmail Email of the person
     * @param friendEmail Email of the friend
     * @param shouldBlock Specify should block or unblock
     * @return PersonConnection
     */
    private PersonConnection blockUnblock(String personEmail, String friendEmail, boolean shouldBlock) {
        PersonConnection personConnection = getPersonConnectionToUpdate(personEmail, friendEmail);
        personConnection.setBlocked(shouldBlock);
        return personConnectionRepository.save(personConnection);
    }

    /**
     * Subscribe to and unsubscribe from connection of person and its friend.
     * @param personEmail Email of the person
     * @param friendEmail Email of the friend
     * @param shouldSubscribe Specify should subscribe or unsubscribe
     * @return PersonConnection
     */
    private PersonConnection subscribeUnsubscribe(String personEmail, String friendEmail, boolean shouldSubscribe) {
        PersonConnection personConnection = getPersonConnectionToUpdate(personEmail, friendEmail);
        personConnection.setSubscribed(shouldSubscribe);
        return personConnectionRepository.save(personConnection);
    }

    /**
     * Cross check against the DB to verify if the person already exists.
     * If so return the retrieved person.
     * Else create new person and save it in the DB and return it.
     * @param person Person to be cross-checked or created
     * @return Existing Person or newly created person
     */
    private Person getPerson(Person person) {
        Person checkedPerson = personService.findByEmail(person.getEmail());
        if (checkedPerson == null) {
            personService.save(person);
            return person;
        }
        return checkedPerson;
    }

    /**
     * Get the Connection from the DB.
     * If the connection doesn't exist in the DB create new one.
     * @param personEmail Email of the person
     * @param friendEmail Email of the friend
     * @return PersonConnection
     */
    private PersonConnection getPersonConnectionToUpdate(String personEmail, String friendEmail) {
        PersonConnection personConnection = personConnectionRepository.findByPersonAndFriendEmail(
                personEmail, friendEmail);
        if (personConnection == null) {
            Person person = getPerson(Person.builder().email(personEmail).build());
            Person friend = getPerson(Person.builder().email(friendEmail).build());
            personConnection = PersonConnection.builder().person(person).friend(friend).build();
        }
        return personConnection;
    }
}
