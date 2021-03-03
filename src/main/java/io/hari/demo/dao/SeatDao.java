package io.hari.demo.dao;

import io.hari.demo.entity.Seat;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
*@Author Hariom Yadav
*@create 02-03-2021
*/
@Repository
public interface SeatDao extends BaseDao<Seat> {

	@Query(value = "select * from seats where id in (select seat_id from seat_lock where show_id = :showId and lock_status = 'available')",
			nativeQuery = true)
    List<Seat> findAllAvailableShowSeats(Long showId);
}
