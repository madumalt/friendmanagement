package com.sps.friendmanagement.util;

import com.sps.friendmanagement.model.Person;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class FriendManagementUtil {
    public List<Person> getPersonList(List<String> emails) {
        return emails.stream().map(email -> Person.builder().email(email).build()).collect(Collectors.toList());
    }
}
