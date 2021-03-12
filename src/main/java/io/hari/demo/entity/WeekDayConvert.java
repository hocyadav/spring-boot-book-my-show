package io.hari.demo.entity;

import io.hari.demo.entity.Days;
import io.hari.demo.entity.WeekDay;

import javax.persistence.AttributeConverter;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @Author Hariom Yadav
 * @create 07-03-2021
 */
public class WeekDayConvert implements AttributeConverter<WeekDay, String> {
    @Override
    public String convertToDatabaseColumn(WeekDay weekDay) {
        if (weekDay == null) return "";
        final List<Days> days = weekDay.getDays();
        final String allDay = days.stream().map(i -> i.toString()).collect(Collectors.joining(","));
        return allDay;
    }

    @Override
    public WeekDay convertToEntityAttribute(String s) {
        if (s == null || s.equals("")) return new WeekDay();
        final String[] split = s.split(",");
        final List<Days> days = Stream.of(split).map(i -> Days.valueOf(i)).collect(Collectors.toList());
        return WeekDay.builder().days(days).build();
    }
}
