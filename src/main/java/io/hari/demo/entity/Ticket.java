package io.hari.demo.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import java.util.ArrayList;
import java.util.List;

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
}

