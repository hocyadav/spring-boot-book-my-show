package io.hari.demo.entity;

import lombok.Getter;
import lombok.ToString;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @Author Hariom Yadav
 * @create 02-03-2021
 */
@Getter
@ToString
@MappedSuperclass
public class BaseEntity implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
}
