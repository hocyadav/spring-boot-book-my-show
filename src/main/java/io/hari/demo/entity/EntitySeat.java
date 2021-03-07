package io.hari.demo.entity;

import lombok.*;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author Hariom Yadav
 * @create 07-03-2021
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class EntitySeat {//not working converter test again // TODO: 07-03-2021
    List<Seat> seats = new ArrayList<>();
}
