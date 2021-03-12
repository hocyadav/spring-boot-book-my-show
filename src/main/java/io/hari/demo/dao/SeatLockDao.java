package io.hari.demo.dao;

import io.hari.demo.entity.SeatLock;
import org.springframework.stereotype.Repository;

/**
 * @Author Hariom Yadav
 * @create 02-03-2021
 */
@Repository
public interface SeatLockDao extends BaseDao<SeatLock> {
    SeatLock findByShowIdAndSeatId(Long showId, Long seatId);
    SeatLock findByShowIdAndSeatIdAndLockStatus(Long showId, Long seatId, String lockStatus);
}
