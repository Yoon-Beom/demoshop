package com.docmall.basic.admin;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * 관리자(Admin) 관련 웹 요청을 처리하는 컨트롤러 클래스입니다.
 *
 * - "/admin/*" 경로로 시작하는 모든 요청을 처리합니다.
 * - 관리자 로그인, 로그아웃, 관리자 메뉴 등 기능을 담당합니다.
 * - @Slf4j: 로그 출력을 위한 Lombok 어노테이션입니다.
 * - @RequiredArgsConstructor: final 필드(의존성) 생성자 자동 생성(Lombok).
 *
 * @author main
 * @since 2025.05.01
 */
@Slf4j // Lombok: 로그 객체 자동 생성(log)
@RequiredArgsConstructor // Lombok: final 필드 생성자 자동 생성(의존성 주입)
@RequestMapping("/admin/*") // "/admin"으로 시작하는 모든 요청을 이 컨트롤러에서 처리
@Controller // 스프링 MVC 컨트롤러 등록
public class AdminController {
    
    // 서비스 및 비밀번호 암호화 객체를 생성자 주입
    private final AdminService adminService;
    private final PasswordEncoder passwordEncoder;
    
    /**
     * 관리자 로그인 폼 페이지로 이동
     *
     * GET /admin/
     * @return 로그인 폼 뷰 이름 (admin/login.html)
     */
    @GetMapping("/")
    public String adminLoginForm() {
        return "admin/ad_login";
    }
    
    /**
     * 관리자 로그인 처리
     *
     * POST /admin/admin_ok
     * - 아이디, 비밀번호 검증 후 세션에 관리자 정보 저장
     * - 실패 시 메시지와 함께 로그인 폼으로 리다이렉트
     *
     * @param dto 로그인 폼에서 전달된 관리자 정보
     * @param session HttpSession (로그인 성공 시 관리자 정보 저장)
     * @param rttr RedirectAttributes (리다이렉트 시 메시지 전달)
     * @return 리다이렉트 경로
     * @throws Exception 예외 발생 시
     */
    @PostMapping("/admin_ok")
    public String admin_ok(
            AdminDTO dto,
            HttpSession session,
            RedirectAttributes rttr
    ) throws Exception {
        
        // 입력받은 아이디로 관리자 정보 조회
        AdminDTO db_vo = adminService.getAdminById(dto.getAd_userid());
        
        String url = "";
        String msg = "";
        
        if(db_vo != null) {
            // 입력 비밀번호(평문)와 DB 비밀번호(암호화) 비교
            if(passwordEncoder.matches(dto.getAd_passwd(), db_vo.getAd_passwd())) {
                // 로그인 성공: 세션에 관리자 정보 저장
                session.setAttribute("admin_auth", db_vo);
                url = "/admin/ad_menu";
            } else {
                // 비밀번호 불일치
                url = "/admin/";
                msg = "pwfail";
            }
        } else {
            // 아이디 없음
            url = "/admin/";
            msg = "idfail";
        }
        
        // 리다이렉트 시 메시지 전달 (Flash Attribute)
        rttr.addFlashAttribute("msg", msg);
        
        // 로그인 성공/실패에 따라 페이지 이동
        return "redirect:" + url;
    }
    
    /**
     * 관리자 메뉴(메인) 페이지
     *
     * GET /admin/ad_menu
     * @return 관리자 메뉴 뷰 이름 (admin/ad_menu.html)
     * @throws Exception 예외 발생 시
     */
    @GetMapping("/ad_menu")
    public String menu() throws Exception {
        return "admin/ad_menu";
    }
}
