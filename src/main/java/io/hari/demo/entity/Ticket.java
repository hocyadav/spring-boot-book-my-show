package io.hari.demo.entity;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.OneToOne;
import javax.persistence.Table;

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

    String paymentMod;

    //other metadata
}
