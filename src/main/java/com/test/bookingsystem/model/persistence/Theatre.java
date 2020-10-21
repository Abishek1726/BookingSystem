package com.test.bookingsystem.model.persistence;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "theatres")
public class Theatre {
    @Id @GeneratedValue @Getter @Setter private Long id;
    @Getter @Setter private String name;

    @OneToMany(targetEntity = Screen.class, fetch = FetchType.LAZY)
    @JoinColumn(name = "theatreId")
    Set<Screen> screens;
}
