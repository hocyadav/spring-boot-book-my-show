package io.hari.demo.dao;

import io.hari.demo.entity.Theatre;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Author Hariom Yadav
 * @create 02-03-2021
 */
@Repository
public interface TheaterDao extends BaseDao<Theatre> {
    List<Theatre> findAllByScreens_Id(Long screenId);
}
