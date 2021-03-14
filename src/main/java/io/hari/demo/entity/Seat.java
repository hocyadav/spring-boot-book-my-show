package io.hari.demo.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import javax.persistence.*;

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

    @JsonProperty
    public Long getSeatId() {
        return super.getId();
    }
}
