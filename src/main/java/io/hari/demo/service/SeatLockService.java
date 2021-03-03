package io.hari.demo.service;

import io.hari.demo.dao.BaseDao;
import io.hari.demo.entity.SeatLock;
import org.springframework.stereotype.Service;

/**
 * @Author Hariom Yadav
 * @create 02-03-2021
 */
@Service
public class SeatLockService extends BaseService<SeatLock> {

    public SeatLockService(BaseDao<SeatLock> dao) {
        super(dao);
    }
}
