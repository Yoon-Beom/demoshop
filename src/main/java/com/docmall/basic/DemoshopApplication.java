package com.docmall.basic;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;

/**
 * 애플리케이션의 메인 클래스입니다.
 */
@MapperScan(basePackages = {"com.docmall.shop.**"}) // MyBatis 매퍼 인터페이스가 위치한 패키지 경로 지정
@SpringBootApplication(exclude = SecurityAutoConfiguration.class) // Spring Security 자동 설정 제외(비활성화)
public class DemoshopApplication {

    /**
     * 애플리케이션의 시작점(Main Method)
     */
    public static void main(String[] args) {
        SpringApplication.run(DemoshopApplication.class, args);
    }

}
