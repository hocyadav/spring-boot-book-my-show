package io.hari.demo.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import javax.persistence.Entity;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import java.time.LocalDateTime;
import java.util.Optional;

/**
 * @Author Hariom Yadav
 * @create 02-03-2021
 */
@Getter
@Setter
@NoArgsConstructor
@ToString(exclude = {"user", "show", "seats"})
@AllArgsConstructor
@Builder
@Entity
@Table(name = "tickets")
public class Ticket extends BaseEntity {
    @OneToOne
    User user;

    @OneToOne
    Show show;

    //	@OneToMany(fetch = FetchType.EAGER)//fetch all
//	List<Seat> seats = new ArrayList<>();
    String seatIds;

    //not working
//    @Convert(converter = EntitySeatConverter.class)
//    EntitySeat entitySeat;

    String paymentMod;

    //other metadata


    @Override
    @JsonProperty
    public Long getId() {
        return super.getId();
    }

    // mapping internal value to show in json response
    @JsonProperty
    public String getShowName() {
          return show.getShowName();
    }

    @JsonProperty
    public LocalDateTime getStartTime() {
        final LocalDateTime startTime = show.getStartTime();
        return startTime;
    }

    @JsonProperty
    public LocalDateTime getEndTime() {
        final LocalDateTime endTime = show.getEndTime();
        return endTime;
    }
    @JsonProperty
    public Long getMovieLength() {
        return show.getMovieLength();
    }

    @JsonProperty
    public Optional<Movie> getMovieName() {
        return show.getMovieNames().stream().findFirst();
    }

    @JsonProperty
    public String getUserName() {
        return user.getName();
    }
}

