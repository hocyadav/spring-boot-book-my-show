package io.hari.demo.dao;

import io.hari.demo.entity.Screen;
import lombok.NonNull;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Author Hariom Yadav
 * @create 02-03-2021
 */
@Repository
public interface ScreenDao extends BaseDao<Screen> {
    List<Screen> findAllByShows_Id(Long showId);
}
