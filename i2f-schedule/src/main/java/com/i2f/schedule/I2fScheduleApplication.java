package com.i2f.schedule;

import i2f.springboot.application.WarBootApplication;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableAspectJAutoProxy
@EnableScheduling
@Slf4j
@SpringBootApplication
public class I2fScheduleApplication {

    public static void main(String[] args) {
        WarBootApplication.startup(WebApplicationType.NONE, I2fScheduleApplication.class, args);
    }

}
