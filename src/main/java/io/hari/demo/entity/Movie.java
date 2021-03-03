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
@ToString(exclude = {})
@AllArgsConstructor
@Builder
@Entity
@Table(name = "movies")
public class Movie extends BaseEntity {
    String movieName;

    //other metadata
}
