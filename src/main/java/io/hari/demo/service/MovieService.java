package io.hari.demo.service;

import io.hari.demo.dao.BaseDao;
import io.hari.demo.entity.Movie;
import org.springframework.stereotype.Service;

/**
 * @Author Hariom Yadav
 * @create 02-03-2021
 */
@Service
public class MovieService extends BaseService<Movie> {

    public MovieService(BaseDao<Movie> dao) {
        super(dao);
    }
}
