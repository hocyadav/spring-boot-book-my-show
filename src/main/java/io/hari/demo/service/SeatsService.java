package io.hari.demo.service;

import io.hari.demo.dao.SeatDao;
import io.hari.demo.dao.SeatLockDao;
import io.hari.demo.entity.Seat;
import io.hari.demo.entity.SeatLock;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.sql.SQLException;
import java.time.Duration;
import java.time.LocalDateTime;

/**
 * @Author Hariom Yadav
 * @create 02-03-2021
 */
@Getter
@Service
@Slf4j
@Transactional // class level
//@Transactional(rollbackOn = { RuntimeException.class })
public class SeatsService extends BaseService<Seat> {

    final SeatDao seatDao;
    final SeatLockDao seatLockDao;

    public SeatsService(SeatDao seatDao, SeatLockDao seatLockDao) {
        super(seatDao);
        this.seatDao = seatDao;
        this.seatLockDao = seatLockDao;
    }

    /**
     * 1 save seat
     * 2 save seat lock
     * 3 add @transaction if any one fail then rollback
     */
//    @Transactional
    public void saveSeatAndLock(Long showId, Seat seat) {
        seatDao.save(seat);
        log.info("seat = {}", seat);
        saveSeatLock(showId, seat);
    }

//    @Transactional //method level
    public void saveSeatLock(Long showId, Seat seat) {
        final SeatLock seatLock = SeatLock.builder()
                .showId(showId)
                .seatId(seat.getId())
                .lockStatus("available")
//                .lockedTime(LocalDateTime.now())
//                .timeoutInSec(Long.valueOf(10))
//                .timeOutDuration(Duration.ofSeconds(10))
                .build();
        seatLockDao.save(seatLock);
        log.info("seatLock = {}",seatLock);

        //not working @Txn
//        if (true) {
//            throw new RuntimeException("query failed : sample exception");
//        }
    }
}
