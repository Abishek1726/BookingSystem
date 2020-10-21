package com.test.bookingsystem.model.persistence;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.test.bookingsystem.converter.ShowPricesConverter;
import com.test.bookingsystem.model.MovieShowPrice;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "movie_shows")
public class MovieShow {
    @Id @Getter @Setter @GeneratedValue Long id;
    @Getter @Setter String movieName;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @Getter @Setter
    @ManyToOne(targetEntity = Screen.class, fetch = FetchType.EAGER)
    @JoinColumn(referencedColumnName = "id")
    private Screen screen;

    @Getter @Setter
    @Temporal(TemporalType.TIME)
    @JsonFormat(pattern="HH:mm")
    private Date startTime;

    @Getter @Setter
    @Temporal(TemporalType.TIME)
    @JsonFormat(pattern="HH:mm")
    private Date endTime;

    @Getter @Setter
    @Column(name = "show_prices_json", columnDefinition = "json")
    @Convert(converter = ShowPricesConverter.class)
    List<MovieShowPrice> showPrices;

}
