package com.i2f.app;

import i2f.springboot.api.EnableApiRespConfig;
import i2f.springboot.application.WarBootApplication;
import i2f.springboot.cors.EnableCorsConfig;
import i2f.springboot.datasource.EnableDynamicDatasourceConfig;
import i2f.springboot.exception.EnableExceptionHandlerConfig;
import i2f.springboot.mybatis.EnableMybatisConfig;
import i2f.springboot.perf.EnablePerfConfig;
import i2f.springboot.redis.EnableRedisConfig;
import i2f.springboot.redisson.EnableRedissonConfig;
import i2f.springboot.secure.EnableSecureConfig;
import i2f.springboot.security.EnableSecurityConfig;
import i2f.springboot.verifycode.EnableVerifyCodeConfig;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@EnableCorsConfig
@EnableSecureConfig
@EnableRedisConfig
@EnableRedissonConfig
@EnableMybatisConfig
@EnableDynamicDatasourceConfig
@EnableSecurityConfig
@EnablePerfConfig
@EnableExceptionHandlerConfig
@EnableApiRespConfig
@EnableVerifyCodeConfig
@EnableAspectJAutoProxy
@Slf4j
@SpringBootApplication(scanBasePackages = "com.i2f")
public class I2fAppApplication {

	public static void main(String[] args) {
		WarBootApplication.startup(I2fAppApplication.class, args);
	}

}
