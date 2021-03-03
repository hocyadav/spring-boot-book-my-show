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
@ToString(exclude = {"screens"})
@AllArgsConstructor
@Builder
@Entity
@Table(name = "theatres")
public class Theatre extends BaseEntity {
    String theaterName;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)// + 1 dao method
    List<Screen> screens = new ArrayList<>();

    //other metadata
}
