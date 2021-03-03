package io.hari.demo.service;

import com.google.common.collect.ImmutableList;
import io.hari.demo.dao.BaseDao;
import io.hari.demo.dao.SeatLockDao;
import io.hari.demo.entity.Seat;
import io.hari.demo.entity.SeatLock;
import io.hari.demo.entity.Show;
import io.hari.demo.entity.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
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

    public UserService(BaseDao<User> dao) {
        super(dao);
    }

    public void selectSeats(Long showId, List<Seat> seats) {
        if (!validSeats(showId, seats)) {
            throw new RuntimeException("one or more seats are not available at this moment");
        }
        seats.stream().forEach(s -> {
            final SeatLock seatLock = seatLockDao.findByShowIdAndSeatId(showId, s.getId());
            seatLock.setLockStatus("temp_lock");
            seatLockDao.save(seatLock);
        });

    }

    private boolean validSeats(Long showId, List<Seat> seats) {
        log.info("showId = " + showId + ", seats = " + seats);
        final List<SeatLock> available = seats.stream().map(seat -> {
            final SeatLock seatLock = seatLockDao.findByShowIdAndSeatId(showId, seat.getId());
            return seatLock;
        }).filter(i -> i.getLockStatus().equals("available")).collect(Collectors.toList());
        log.info("available = " + available);
        return available.size() == seats.size() ? true : false;
    }

    public void paymentFail_Or_UnexpectedError(User user, Show show, List<Seat> seats) {
        //reset lock status
        seats.forEach(seat -> {
            final SeatLock seatLock = seatLockDao.findByShowIdAndSeatId(show.getId(), seat.getId());
            seatLock.setLockStatus("available");
            seatLockDao.save(seatLock);
        });
    }
}
