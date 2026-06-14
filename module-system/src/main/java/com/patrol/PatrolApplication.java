package com.patrol;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * 机房巡检系统 - 应用启动类
 *
 * @author patrol-team
 */
@SpringBootApplication(scanBasePackages = "com.patrol")
@MapperScan("com.patrol.**.mapper")
public class PatrolApplication {

    public static void main(String[] args) {
        SpringApplication.run(PatrolApplication.class, args);
    }
}
