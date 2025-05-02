package com.docmall.basic;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;

/**
 * Spring Boot 애플리케이션의 메인 클래스입니다.
 *
 * - 이 클래스에서 main() 메서드를 실행하면 내장 Tomcat 서버가 실행되고,
 *   Spring Boot 기반 애플리케이션이 시작됩니다.
 * - @SpringBootApplication은 스프링 부트의 핵심 설정을 자동으로 활성화합니다.
 * - @MapperScan은 MyBatis 매퍼 인터페이스가 위치한 패키지를 지정합니다.
 * - SecurityAutoConfiguration.class를 exclude하여 Spring Security 자동 설정을 비활성화합니다.
 *
 * @author main
 * @since 2025.05.01
 */
@MapperScan(basePackages = {"com.docmall.basic.**"})
// MyBatis 매퍼 인터페이스(@Mapper)가 위치한 패키지를 지정하여, 자동으로 빈으로 등록해줍니다.
// 공식 문서: https://mybatis.org/spring-boot-starter/mybatis-spring-boot-autoconfigure/
@SpringBootApplication(exclude = SecurityAutoConfiguration.class)
// @SpringBootApplication: @Configuration, @EnableAutoConfiguration, @ComponentScan을 합친 어노테이션
// exclude 옵션으로 Spring Security 자동 설정을 제외(로그인/인증 없이 개발 가능)
public class DemoshopApplication {

    /**
     * 애플리케이션의 시작점(Main Method)
     *
     * @param args 커맨드라인 인자
     */
    public static void main(String[] args) {
        // Spring Boot 애플리케이션을 실행 (내장 Tomcat 서버 구동)
        SpringApplication.run(DemoshopApplication.class, args);
    }
}
