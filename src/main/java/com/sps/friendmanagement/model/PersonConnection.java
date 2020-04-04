package com.sps.friendmanagement.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Objects;

@Entity
@Table(name = "person_connection")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PersonConnection implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "person_connection_id")
    private Long id;

    @NotNull
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "person_id")
    private Person person;

    @NotNull
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "friend_id")
    private Person friend;

    @Column(name = "subscribed")
    private boolean isSubscribed;

    @Column(name = "blocked")
    private boolean isBlocked;

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if(obj == null || getClass() != obj.getClass()) return false;
        PersonConnection that = (PersonConnection) obj;
        return isBlocked() == that.isBlocked() &&
                isSubscribed() == that.isSubscribed() &&
                getPerson().equals(that.getPerson()) &&
                getFriend().equals((that.getFriend()));
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getPerson(), getFriend(), isSubscribed(), isBlocked());
    }
}
