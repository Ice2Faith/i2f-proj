package com.i2f.schedule.job;

import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * @author Ice2Faith
 * @date 2023/3/24 23:21
 * @desc
 */
@Slf4j
@Component
public class HelloJob {

    private DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss SSS");

    @Scheduled(fixedRate = 3000L, initialDelay = 0L)
    public void hello() {
        log.info("定时任务时间：" + LocalDateTime.now().format(formatter));
    }
}
