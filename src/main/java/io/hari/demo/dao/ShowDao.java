package io.hari.demo.dao;

import io.hari.demo.entity.Show;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * @Author Hariom Yadav
 * @create 02-03-2021
 */
@Repository
public interface ShowDao extends BaseDao<Show> {

//    @Query(value = "insert into shows_seats(show_id, seats_id) values(1, 5)",
//    @Query(value = "insert into shows_seats(show_id, seats_id) select :showId, :seatId",
    @Query(value = "insert into shows_seats(show_id, seats_id) values (:showId, :seatId)",
            nativeQuery = true)
    @Modifying //https://thorben-janssen.com/spring-data-jpa-query-annotation/
    void addNewShowSeat(Long showId, Long seatId);//not working
//    void addNewShowSeat(@Param("showId") Long showId, @Param("seatsId") Long seatId);//not working
    //insert into shows_seats(show_id, seats_id) values(1, 5); //working sql


    // https://thorben-janssen.com/change-before-persist/
    // https://www.netsurfingzone.com/jpa/spring-data-jpa-modifying-annotation-example/
    // https://stackoverflow.com/questions/45747810/spring-data-jpa-to-insert-using-query-modifying-without-using-nativequery-or
//    @Modifying
//    @Query(value = "insert into Student (id,student_name,roll_number, university) "
//            + "VALUES(:id,:studentName,:rollNumber,:university)",
//            nativeQuery = true)
//    public void insertStudentUsingQueryAnnotation(@Param("id") int id,
//                                                  @Param("studentName") String studentName,
//                                                  @Param("rollNumber") String rollNumber,
//                                                  @Param("university") String university);


}
