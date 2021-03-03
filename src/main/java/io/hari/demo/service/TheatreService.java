package io.hari.demo.service;

import io.hari.demo.dao.BaseDao;
import io.hari.demo.entity.Theatre;
import org.springframework.stereotype.Service;

/**
 * @Author Hariom Yadav
 * @create 02-03-2021
 */
@Service
public class TheatreService extends BaseService<Theatre> {
    public TheatreService(BaseDao<Theatre> theatreDao) {
        super(theatreDao);
    }
}
