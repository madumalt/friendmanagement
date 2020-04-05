package com.sps.friendmanagement.util;

import com.sps.friendmanagement.model.Person;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = FriendManagementUtil.class)
public class FriendManagementUtilTest {

    @Autowired
    private FriendManagementUtil friendManagementUtil;

    @Test
    public void getPersonList() {
        List<String> emailList = Arrays.asList("a@gmail.com", "b@gmail.com");

        List<Person> result = friendManagementUtil.getPersonList(emailList);

        assertThat(result).isNotNull();
        assertThat(result.size()).isEqualTo(2);
        assertThat(result.get(0).getEmail()).isEqualTo(emailList.get(0));
        assertThat(result.get(1).getEmail()).isEqualTo(emailList.get(1));
    }

    @Test
    public void getPersonLisEmptyList() {
        List<String> emailList = Collections.emptyList();

        List<Person> result = friendManagementUtil.getPersonList(emailList);

        assertThat(result).isNotNull();
        assertThat(result).isEmpty();
    }

    @Test
    public void extractEmailsFromEmailContainingText() {
        String text = "Hello Wordl! with a@gmail.com b@gmail.com recipients";

        List<String> result = friendManagementUtil.extractEmails(text);

        assertThat(result).isNotEmpty();
        assertThat(result.size()).isEqualTo(2);
        assertThat(result).contains("a@gmail.com");
        assertThat(result).contains("b@gmail.com");
    }

    @Test
    public void extractEmailsFromNoEmailContainingText() {
        String text = "Hello Wordl! with no recipient :(";

        List<String> result = friendManagementUtil.extractEmails(text);

        assertThat(result).isEmpty();
    }
}
