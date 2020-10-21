package com.test.bookingsystem.model.persistence;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.test.bookingsystem.converter.ShowSeatsInfoConverter;
import com.test.bookingsystem.model.ShowSeatsInfo;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;

//TODO: Should Implement ArchivedShows
@Entity
@Table(name = "open_shows",
       uniqueConstraints = {
        @UniqueConstraint(name="duplicate_show_constraint", columnNames = {"movie_show_id", "showDate"})
       })

public class OpenShow {
    @Id @GeneratedValue @Getter @Setter Long id;

    @Getter @Setter
    @ManyToOne(targetEntity = MovieShow.class, fetch = FetchType.EAGER)
    @JoinColumn(referencedColumnName = "id")
    @JsonIgnore
    private MovieShow movieShow;

    @Getter @Setter
    @Temporal(TemporalType.DATE)
    private Date showDate;

    @Getter @Setter
    @Column(name = "show_seat_details", columnDefinition = "json")
    @Convert(converter = ShowSeatsInfoConverter.class)
    private ShowSeatsInfo showSeatsInfo;
}
