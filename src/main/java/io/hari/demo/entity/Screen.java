package io.hari.demo.entity;

import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 * @Author Hariom Yadav
 * @create 02-03-2021
 */
@Getter
@Setter
@NoArgsConstructor
@ToString(exclude = {"shows"})
@AllArgsConstructor
@Builder
@Entity
@Table(name = "screens")
public class Screen extends BaseEntity {
    String screenName;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)//+ 1 dao method
    List<Show> shows = new ArrayList<>();

    // other metadata
}
