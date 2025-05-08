package com.docmall.basic.common.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * 비밀번호 암호화를 위한 PasswordEncoder 빈(Bean) 등록 설정 클래스입니다.
 *
 * - 스프링 시큐리티의 PasswordEncoder는 비밀번호를 안전하게 암호화/검증하는 데 사용됩니다.
 * - BCryptPasswordEncoder는 BCrypt 해시 알고리즘을 사용해 강력한 암호화를 제공합니다.
 * - @Bean으로 등록하면, 프로젝트 전체에서 의존성 주입(@Autowired, @RequiredArgsConstructor 등)으로 사용할 수 있습니다.
 * - 별도의 Spring Security 전체 설정 없이, 비밀번호 암호화 기능만 사용할 때도 이 방식이 표준입니다.
 *
 * 공식 문서: https://docs.spring.io/spring-security/reference/features/authentication/password-storage.html
 *
 * @author main
 * @since 2025.05.01
 */
@Configuration // 스프링 설정 클래스임을 명시
public class SecurityConfig {

    /**
     * PasswordEncoder 빈 등록
     * 
     * - BCryptPasswordEncoder는 비밀번호를 해시(암호화)하고, 
     *   matches() 메서드로 평문과 해시값을 비교할 수 있습니다.
     * - 회원가입, 로그인 등에서 안전하게 비밀번호를 처리할 때 사용합니다.
     *
     * @return PasswordEncoder 구현체(BCrypt)
     */
	@Bean
	PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
}
