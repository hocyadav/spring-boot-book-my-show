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
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class WeekDay {
    List<Days> days = new ArrayList<>();
}
