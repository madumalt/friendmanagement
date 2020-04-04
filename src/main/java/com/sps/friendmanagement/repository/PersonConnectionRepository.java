package com.sps.friendmanagement.repository;

import com.sps.friendmanagement.model.PersonConnection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PersonConnectionRepository extends JpaRepository<PersonConnection, Long> {
    List<PersonConnection> findAll();

    @Query("SELECT pc FROM PersonConnection pc INNER JOIN pc.person p WHERE p.email = :email")
    List<PersonConnection> findByPersonEmail(@Param("email") String email);

    @Query("SELECT pc FROM PersonConnection pc INNER JOIN pc.person p " +
            "WHERE pc.friend.email = :email AND pc.isBlocked = false")
    List<PersonConnection> findByFriendEmailNotBlocked(@Param("email") String email);

    @Query("SELECT pc FROM PersonConnection pc INNER JOIN pc.person p " +
            "WHERE p.email = :personEmail AND pc.friend.email = :friendEmail")
    PersonConnection findByPersonAndFriendEmail(@Param("personEmail") String personEmail,
                                                @Param("friendEmail") String friendEmail);
}
