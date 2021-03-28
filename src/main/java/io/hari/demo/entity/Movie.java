package io.hari.demo.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.util.HashMap;
import java.util.Map;

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
    Float ratings;

    @Convert(converter = MovieAttributeConvertor.class)//move to base entity and test
    MovieAttribute movieAttribute;

    @Transient
    Map<String, String> attributes = new HashMap<>();

    @JsonIgnore//optional : hide from json web response, coz already above we are showing movie attribute
    public Map<String, String> getAttributes() {
        return movieAttribute.getAttributes();
    }
}
