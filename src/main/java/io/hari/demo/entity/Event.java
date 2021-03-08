package io.hari.demo.entity;

import lombok.*;
import org.springframework.context.ApplicationEvent;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

/**
 * @Author Hariom Yadav
 * @create 07-03-2021
 */
@AllArgsConstructor
@Builder
@ToString
@Getter
@Setter
@NoArgsConstructor
public class Event {
    String name;
    String type;
    Map<String, Object> params = new HashMap<>();
    LocalDateTime localDateTime;
}
