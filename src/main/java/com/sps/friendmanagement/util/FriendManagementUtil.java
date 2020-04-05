package com.sps.friendmanagement.util;

import com.sps.friendmanagement.model.Person;
import org.hibernate.mapping.Collection;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Component
public class FriendManagementUtil {
    public List<Person> getPersonList(List<String> emails) {
        return emails.stream().map(email -> Person.builder().email(email).build()).collect(Collectors.toList());
    }

    public List<String> extractEmails(String text) {
        Set<String> emails = new HashSet<>();

        Pattern VALID_EMAIL_ADDRESS_REGEX = Pattern.compile(
                "([a-z0-9_.-]+)@([a-z0-9_.-]+[a-z])",
                Pattern.CASE_INSENSITIVE);

        Matcher matcher = VALID_EMAIL_ADDRESS_REGEX .matcher(text);
        while (matcher.find()) {
            emails.add(matcher.group().trim());
        }

        return new ArrayList<>(emails);
    }
}
