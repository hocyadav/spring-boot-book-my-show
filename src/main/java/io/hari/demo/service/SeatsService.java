package io.hari.demo.service;

import io.hari.demo.dao.SeatDao;
import io.hari.demo.dao.SeatLockDao;
import io.hari.demo.entity.Seat;
import lombok.Getter;
import org.springframework.stereotype.Service;

/**
 * @Author Hariom Yadav
 * @create 02-03-2021
 */
@Getter
@Service
public class SeatsService extends BaseService<Seat> {

    final SeatLockDao seatLockDao;

    public SeatsService(SeatDao seatDao, SeatLockDao seatLockDao) {
        super(seatDao);
        this.seatLockDao = seatLockDao;
    }
}
