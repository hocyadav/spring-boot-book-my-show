package io.hari.demo.entity;

import lombok.*;
import org.springframework.beans.factory.annotation.Value;

import javax.persistence.Entity;
import javax.persistence.PrePersist;
import java.time.Duration;
import java.time.LocalDateTime;

/**
 * @Author Hariom Yadav
 * @create 02-03-2021
 */
@Getter
@Setter
@NoArgsConstructor
@ToString(exclude = {}, callSuper = true)
@AllArgsConstructor
@Builder
@Entity
public class SeatLock extends BaseEntity {
    Long showId;
    Long seatId;
    String lockStatus;//available, locked
    //other metadata - timeout

    LocalDateTime lockedTime;

    Long timeoutInSec;//m1 : we can store timeout in Long
    Duration timeOutDuration;// m2 : we can store timeout in duration object

    //m1 check
    public boolean lockExpiredCheck() {//todo DONE : working : period class, duration class, instance class
        final Duration between = Duration.between(lockedTime, LocalDateTime.now());
        return between.getSeconds() - timeoutInSec >= 0 ? true : false;
    }
    //m2 check
    public boolean lockExpiredUsingDuration() {//TODO done : working
        System.out.println("compareTo = " + Duration.ofSeconds(10).compareTo(Duration.ofSeconds(10)));//0
        System.out.println("compareTo = " + Duration.ofSeconds(10).compareTo(Duration.ofSeconds(100)));//-1
        System.out.println("compareTo = " + Duration.ofSeconds(100).compareTo(Duration.ofSeconds(10)));//1

        final Duration myTimeDiff = Duration.between(lockedTime, LocalDateTime.now());//10
        final int diffWithTimeout = myTimeDiff.compareTo(timeOutDuration);//10 - 100 = -90 = -1, allow me
        //case 2 : diff 10 , diff with timeout = 90 that means timeout
        return diffWithTimeout < 0 ? false : true;
//        return diffWithTimeout >= 0 ? true : false;//m2
    }

    @PrePersist
    public void prePersist() {
        if (timeOutDuration == null) {
            timeOutDuration = Duration.ofSeconds(100);
        }

        if (timeoutInSec == null) {
            timeoutInSec = Long.valueOf(300);
        }
    }

}
