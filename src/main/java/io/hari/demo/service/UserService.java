package io.hari.demo.service;

import com.google.common.collect.ImmutableMap;
import io.hari.demo.dao.BaseDao;
import io.hari.demo.dao.SeatLockDao;
import io.hari.demo.entity.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @Author Hariom Yadav
 * @create 02-03-2021
 */
@Service
@Slf4j
public class UserService extends BaseService<User> {

    @Autowired
    SeatLockDao seatLockDao;

    @Autowired
    ApplicationEventPublisher applicationEventPublisher;

    @Autowired
    NotificationService notificationService;

    public UserService(BaseDao<User> dao) {
        super(dao);
    }

    public synchronized void selectSeats(Long showId, List<Seat> seats) {
        if (!validSeats(showId, seats)) {
            throw new RuntimeException("one or more seats are not available at this moment");
        }
        seats.forEach(s -> changeSeatLockStatus(s, showId, "temp_lock"));

        //event use when user selected seats but not booked, so scheduler will mark them as availablea after timeout
        final Event event = Event.builder()
                .name("event1")
                .type("type1").params(ImmutableMap.of("showId", showId, "seats", seats))
                .localDateTime(LocalDateTime.now()).build();
        applicationEventPublisher.publishEvent(event);
    }

    private synchronized boolean validSeats(Long showId, List<Seat> requestedSeats) {
        log.info("showId = " + showId + ", requestedSeats = " + requestedSeats);
        final List<SeatLock> actualAvailableSeats = requestedSeats.stream()
                .map(seat -> {
//                    return seatLockDao.findByShowIdAndSeatId(showId, seat.getId());
                    return seatLockDao.findByShowIdAndSeatIdAndLockStatus(showId, seat.getId(), "available");
                })
//                .filter(i -> i.getLockStatus().equals("available"))//above already we fetched based on status, so not reauired
                .collect(Collectors.toList());
        log.info("actualAvailableSeats = " + actualAvailableSeats);
        return actualAvailableSeats.size() == requestedSeats.size() ? true : false;
    }

    public synchronized void paymentFail_Or_UnexpectedError(User currentUser, Show selectedShow, List<Seat> selectedSeats) {
        //reset lock status
        selectedSeats.forEach(seat -> {
            changeSeatLockStatus(seat, selectedShow.getId(), "available");
            log.info("seat lock reset for {} {}", selectedShow.getId(), seat.getId());
        });

        notificationService.sendNotificationToUser(currentUser);//optional, create a notification service
    }

    public synchronized void changeSeatLockStatus(final Seat seat, final Long showId, final String newStatus) {
        final SeatLock seatLock = seatLockDao.findByShowIdAndSeatId(showId, seat.getId());
        seatLock.setLockStatus(newStatus);
        seatLockDao.save(seatLock);
    }

    private void sendNotificationToUser(User user) {
        log.info("payment failed for user {} , please select and book ticket again", user.getName());
    }
}
