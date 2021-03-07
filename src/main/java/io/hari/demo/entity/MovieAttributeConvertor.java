package io.hari.demo.entity;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;

import javax.persistence.AttributeConverter;
import java.util.Objects;

/**
 * @Author Hariom Yadav
 * @create 07-03-2021
 */
public class MovieAttributeConvertor implements AttributeConverter<MovieAttribute, String> {
    ObjectMapper objectMapper = new ObjectMapper();

    @SneakyThrows
    @Override
    public String convertToDatabaseColumn(MovieAttribute movieAttribute) {
        if (movieAttribute == null) return "";
        return objectMapper.writeValueAsString(movieAttribute);
    }

    @SneakyThrows
    @Override
    public MovieAttribute convertToEntityAttribute(String s) {
        if (s == null ) return new MovieAttribute();
        return objectMapper.readValue(s, MovieAttribute.class);
    }
}
