package io.hari.demo.entity;

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

    @Convert(converter = MovieAttributeConvertor.class)
    MovieAttribute movieAttribute;

    @Transient
    Map<String, String> attributes = new HashMap<>();

    public Map<String, String> getAttributes() {
        return movieAttribute.getAttributes();
    }
}
