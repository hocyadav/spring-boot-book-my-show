package io.hari.demo.service;

import io.hari.demo.dao.BaseDao;
import io.hari.demo.entity.Screen;
import org.springframework.stereotype.Service;

/**
 * @Author Hariom Yadav
 * @create 02-03-2021
 */
@Service
public class ScreenService extends BaseService<Screen> {

    public ScreenService(BaseDao<Screen> ScreenDao) {
        super(ScreenDao);
    }
}
