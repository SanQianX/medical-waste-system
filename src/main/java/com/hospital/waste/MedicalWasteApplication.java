package com.hospital.waste;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * 医院医疗废物管理系统启动类
 */
@SpringBootApplication
@MapperScan("com.hospital.waste.mapper")
@EnableScheduling
public class MedicalWasteApplication {

    public static void main(String[] args) {
        SpringApplication.run(MedicalWasteApplication.class, args);
    }
}
