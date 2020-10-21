package com.test.bookingsystem.model.persistence;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.test.bookingsystem.converter.ScreenSeatLayoutConverter;
import com.test.bookingsystem.model.ScreenSeatLayout;
import com.test.bookingsystem.model.persistence.MovieShow;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "screens")
public class Screen {
    @Getter @Setter @Id @GeneratedValue private Long id;
    @Getter @Setter private String name;
    @Getter @Setter private Long theatreId;

    @Getter @Setter
    @Column(columnDefinition = "json")
    @Convert(converter = ScreenSeatLayoutConverter.class)
    private ScreenSeatLayout seatLayout;

    @JsonIgnore
    @OneToMany(mappedBy = "screen", fetch = FetchType.LAZY)
    @Getter @Setter private Set<MovieShow> movieShows;
}

