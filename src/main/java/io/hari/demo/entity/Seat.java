package io.hari.demo.entity;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.Table;

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
@Table(name = "seats")
public class Seat extends BaseEntity {
    String seatType = "silver";//default value
    //other metadata GOLD, SILVER
}
