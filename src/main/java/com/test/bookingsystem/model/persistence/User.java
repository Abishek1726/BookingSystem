package com.test.bookingsystem.model.persistence;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "users", uniqueConstraints = {
        @UniqueConstraint(name = "name_unique", columnNames = {"name"})
       })
public class User {
    public enum Role { USER, ADMIN }

    @Getter @Setter @Id @GeneratedValue Long id;
    @Getter @Setter
    String name;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @Getter @Setter String password;

    @Getter @Setter Role role;

    @JsonIgnore
    public boolean isAdmin() {
        return role.equals(Role.ADMIN);
    }
}
