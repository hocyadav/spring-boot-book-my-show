package io.hari.demo.entity;

import lombok.*;

import java.util.HashMap;
import java.util.Map;

/**
 * @Author Hariom Yadav
 * @create 07-03-2021
 */
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@ToString
public class MovieAttribute {
    Map<String, String> attributes = new HashMap<>();
}
