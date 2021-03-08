package io.hari.demo.event;

import io.hari.demo.entity.Event;
import io.hari.demo.entity.Seat;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @Author Hariom Yadav
 * @create 07-03-2021
 */
@Component
public class ConsumerService {
    public static ConcurrentHashMap<String, Event> concurrentHashMap = new ConcurrentHashMap<>();

    @EventListener
    public void consumerStart(Event event) {
        System.out.println("event consuming = " + event);
        final Map<String, Object> params = event.getParams();
        final Long showId = (Long) params.get("showId");
        final List<Seat> seats = (List<Seat>) params.get("seats");
        final LocalDateTime localDateTime = event.getLocalDateTime();
        System.out.println("showId = " + showId);
        System.out.println("seats = " + seats);
        System.out.println("localDateTime = " + localDateTime);
        concurrentHashMap.put(event.getName(), event);
    }
}
