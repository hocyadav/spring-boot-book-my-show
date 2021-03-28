package io.hari.demo.entity;

import lombok.*;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.ArrayList;
import java.util.List;

/**
 * @Author Hariom Yadav
 * @create 02-03-2021
 */
@Getter
@Setter
@NoArgsConstructor
@ToString(exclude = {"shows"}, callSuper = true)
@AllArgsConstructor
@Builder
@Entity
@Table(name = "users")
public class User extends BaseEntity {

    String name;

    //	@OneToMany(fetch = FetchType.EAGER)//remove cascade and store in db before passing here
    @OneToMany(cascade = CascadeType.ALL)
    List<Ticket> tickets = new ArrayList<>();//TODO : change to Enum and inside enum store List<Integer> ticket id,
    // so that every time we can easily add new ticket

    //other metadata;
}
