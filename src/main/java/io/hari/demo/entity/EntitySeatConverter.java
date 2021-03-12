package io.hari.demo.entity;

import io.hari.demo.dao.SeatDao;
import io.hari.demo.entity.EntitySeat;
import io.hari.demo.entity.Seat;
import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.AttributeConverter;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @Author Hariom Yadav
 * @create 07-03-2021
 */
public class EntitySeatConverter implements AttributeConverter<EntitySeat, String> {//m1 using collect join , m2 using object mapper

    @Autowired
    SeatDao seatDao;

    @Override
    public String convertToDatabaseColumn(EntitySeat entitySeat) {
        if (entitySeat == null) return null;
        final List<Seat> seats = entitySeat.getSeats();
        final String dbString = seats.stream().map(i -> i.getId().toString()).collect(Collectors.joining(","));
        return dbString;
    }

    @Override
    public EntitySeat convertToEntityAttribute(String s) {
        if (s == null) return null;
        final String[] split = s.split(",");
        final List<Seat> seats = Stream.of(split).map(i -> {
            System.out.println("i = " + i);
            final Seat silver = Seat.builder().seatType("silver").build();
//            final Optional<Seat> seat = seatDao.findById(Long.valueOf(i));
            return silver;
        }).collect(Collectors.toList());
        return EntitySeat.builder().seats(seats).build();
    }
}
