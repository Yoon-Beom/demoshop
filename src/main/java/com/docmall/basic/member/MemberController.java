package com.docmall.basic.member;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * 회원 관련 웹 요청을 처리하는 컨트롤러 클래스입니다.
 *
 * - "/member"로 시작하는 모든 요청을 이 컨트롤러에서 처리합니다.
 *   예: /member/login, /member/join, /member/idCheck 등
 * - @Controller: 스프링 MVC 컨트롤러임을 명시합니다.
 * - @RequestMapping("/member"): 클래스 레벨에서 URL 경로 접두어를 지정합니다.
 * - @RequiredArgsConstructor: final 필드(예: MemberService) 생성자 자동 생성(Lombok).
 * - @Slf4j: 로그 출력을 위한 Lombok 어노테이션입니다.
 *
 * @author main
 * @since 2025.05.01
 */
@Slf4j // Lombok: 로그 출력을 위한 Logger 객체(log)를 자동 생성합니다.
@RequiredArgsConstructor // Lombok: final 필드 생성자 자동 생성(의존성 주입에 사용)
@RequestMapping("/member") // "/member"로 시작하는 요청을 이 컨트롤러에서 처리
@Controller // 스프링 MVC 컨트롤러로 등록
public class MemberController {

    // 회원 서비스(비즈니스 로직 담당)
    private final MemberService memberService;

    // 비밀번호 암호화용 PasswordEncoder (스프링 시큐리티 제공)
    // 공식 문서: https://docs.spring.io/spring-security/reference/features/authentication/password-storage.html
    private final PasswordEncoder passwordEncoder;

    /**
     * 회원 가입 페이지로 이동하는 요청을 처리합니다.
     *
     * GET /member/join
     *
     * @throws Exception 예외 발생 시
     */
    @GetMapping("/join")
    public void join() throws Exception {
        // 반환 타입이 void이므로, 요청 경로와 동일한 뷰(/templates/member/join.html)를 렌더링합니다.
        // 별도의 로직이 없으므로 화면 이동만 처리합니다.
    }

    /**
     * 아이디 중복 체크 요청을 처리합니다.
     *
     * GET /member/idCheck?mbsp_id=xxx
     * - 사용 가능한 아이디면 "yes", 이미 존재하면 "no"를 반환합니다.
     * - ResponseEntity를 사용하여 HTTP 상태 코드와 함께 결과를 반환합니다.
     *
     * @param mbsp_id 중복 체크할 회원 아이디
     * @return "yes" 또는 "no" 문자열과 HTTP 200 OK 상태
     * @throws Exception 예외 발생 시
     */
    @GetMapping("/idCheck")
    public ResponseEntity<String> idCheck(String mbsp_id) throws Exception {
        // memberService.idCheck()가 null이 아니면 이미 존재하는 아이디
        String isUse = (memberService.idCheck(mbsp_id) != null) ? "no" : "yes";

        // ResponseEntity: HTTP 응답 본문과 상태 코드를 함께 반환하는 객체
        return new ResponseEntity<>(isUse, HttpStatus.OK);
    }

    /**
     * 회원 가입 처리 요청(POST)
     *
     * POST /member/join
     * - 회원가입 폼에서 입력받은 정보를 처리하여 DB에 저장합니다.
     * - 비밀번호는 반드시 암호화하여 저장합니다.
     * - 가입 후 로그인 페이지로 리다이렉트합니다.
     *
     * @param vo 회원 정보(MemberVO)
     * @return 로그인 페이지로 리다이렉트
     */
    @PostMapping("/join")
    public String join(MemberVO vo) {

        // 비밀번호 암호화(스프링 시큐리티 PasswordEncoder 사용)
        // encode() 메서드는 입력받은 평문 비밀번호를 암호화된 문자열로 변환합니다.
        vo.setMbsp_password(passwordEncoder.encode(vo.getMbsp_password()));

        log.info("회원정보 비밀번호 암호화 후: {}", vo);

        // DB에 회원 정보 저장
        memberService.join(vo);

        // 회원가입 후 로그인 페이지로 이동(리다이렉트)
        return "redirect:/member/login";
    }
}
