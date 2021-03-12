package io.hari.demo.service;

import io.hari.demo.entity.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * @Author Hariom Yadav
 * @create 11-03-2021
 */
@Service
@Slf4j
public class NotificationService {

    //async method
    public void sendNotificationToUser(User user) {
        log.info("payment failed for user {} , please select and book ticket again", user.getName());
    }
}
