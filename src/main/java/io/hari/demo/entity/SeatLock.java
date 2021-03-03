package io.hari.demo.entity;

import lombok.*;

import javax.persistence.Entity;

/**
 * @Author Hariom Yadav
 * @create 02-03-2021
 */
@Getter
@Setter
@NoArgsConstructor
@ToString(exclude = {}, callSuper = true)
@AllArgsConstructor
@Builder
@Entity
public class SeatLock extends BaseEntity {
    Long showId;
    Long seatId;
    String lockStatus;//available, locked
    //other metadata - timeout
}
