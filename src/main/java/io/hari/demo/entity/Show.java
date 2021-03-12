package io.hari.demo.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @Author Hariom Yadav
 * @create 02-03-2021
 */
@Getter
@Setter
@NoArgsConstructor
@ToString(exclude = {"seats", "movies"}, callSuper = true)
@AllArgsConstructor
@Builder
@Entity
@Table(name = "shows")
public class Show extends BaseEntity {
    String showName;

    @ManyToMany(fetch = FetchType.EAGER)//+ 1 dao method
    List<Seat> seats = new ArrayList<>();

    @OneToMany
//			(fetch = FetchType.EAGER)//not working - error cant fetch multiple bags since above also we are fetching
    //test locally how to fetch or fetch at run time and see
    List<Movie> movies = new ArrayList<>();

    //other metadata
    LocalDateTime startTime; //only LocalTime is enough here , TODO
    LocalDateTime endTime;
    Long movieLength;//start - end time

    @JsonIgnore
    @Convert(converter = WeekDayConvert.class)
    WeekDay weekDay;//this is simple pojo and inside that also simple pojo list


    @Transient
    List<Days> days = new ArrayList<>();
    public List<Days> getDays() {// in weekday enum we are showing values
        return weekDay.getDays();
    }

//    @JsonProperty(value = "seat_types")
//    public Map<String, List<Long>> getSeatsTypes() {
//        final Map<String, List<Long>> collect = seats.stream().collect(Collectors.groupingBy(
//                i -> i.getSeatType(),
//                Collectors.mapping(i -> i.getSeatId(), Collectors.toList())
//        ));
//        return collect;
//    }

    @JsonProperty
    public List<Movie> getMovieNames() {
        return movies;
    }
}
