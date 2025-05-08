package com.docmall.basic.member;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.docmall.basic.mail.EmailDTO;

import jakarta.servlet.http.HttpSession;
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
    public String join(MemberVO vo) throws Exception {

        // 비밀번호 암호화(스프링 시큐리티 PasswordEncoder 사용)
        // encode() 메서드는 입력받은 평문 비밀번호를 암호화된 문자열로 변환합니다.
        vo.setMbsp_password(passwordEncoder.encode(vo.getMbsp_password()));

        log.info("회원정보 비밀번호 암호화 후: {}", vo);

        // DB에 회원 정보 저장
        memberService.join(vo);

        // 회원가입 후 로그인 페이지로 이동(리다이렉트)
        return "redirect:/member/login";
    }
    
    /**
     * 로그인 페이지를 보여주는 GET 요청 처리
     *
     * GET /member/login
     *
     * @throws Exception 예외 발생 시
     */
    @GetMapping("/login")
    public void login() throws Exception {
        // 반환 타입이 void이므로, "/member/login" 요청 시 templates/member/login.html 뷰를 렌더링합니다.
        // 별도의 로직이 없으므로 화면 이동만 처리합니다.
    }

    /**
     * 로그인 처리 요청(POST)
     *
     * POST /member/login
     * - 사용자가 입력한 아이디/비밀번호를 확인하여 로그인 처리
     * - 로그인 성공 시 세션에 회원정보를 저장하고, 이전 요청 URL 또는 메인 페이지로 이동
     * - 로그인 실패 시 상태(status)를 리다이렉트 속성에 담아 로그인 페이지로 이동
     *
     * @param dto    로그인 폼에서 입력받은 아이디/비밀번호 DTO
     * @param session HTTP 세션 객체 (로그인 정보 저장에 사용)
     * @param rttr   리다이렉트 시 1회성 메시지 전달용 객체
     * @return 리다이렉트할 URL
     * @throws Exception 예외 발생 시
     */
    @PostMapping("/login")
    public String login(LoginDTO dto, HttpSession session, RedirectAttributes rttr) throws Exception {
        
        // 아이디로 회원 정보 조회
        MemberVO memberVO = memberService.login(dto.getMbsp_id());
        
        String url = "";
        String status = "";
        
        if(memberVO != null) {
            // 입력한 비밀번호와 DB에 저장된 비밀번호(암호화)를 비교
            if(passwordEncoder.matches(dto.getMbsp_password(), memberVO.getMbsp_password())) {
                // 비밀번호 일치: 로그인 성공
                memberVO.setMbsp_password(""); // 보안을 위해 비밀번호 정보는 세션에 저장하지 않음
                session.setAttribute("login_auth", memberVO); // 세션에 로그인 정보 저장

                // 로그인 전 이동하려던 페이지가 있다면 해당 URL로 이동
                if(session.getAttribute("targetUrl") != null) {
                    url = (String) session.getAttribute("targetUrl");

                    // POST 방식 데이터가 있으면 URL에 쿼리스트링으로 추가
                    if(session.getAttribute("postData") != null) {
                        log.info("데이터 : " + session.getAttribute("postData"));
                        url = url + "?" + (String) session.getAttribute("postData");
                    }
                } else {
                    // 기본: 메인 페이지로 이동
                    url = "/";
                }
            } else {
                // 비밀번호 불일치
                status = "pwFail";
                url = "/member/login";
            }
        } else {
            // 아이디 존재하지 않음
            status = "idFail";
            url = "/member/login";
        }

        // 로그인 실패 시 상태 정보를 1회성 속성으로 전달
        rttr.addFlashAttribute("status", status);
        
        // 최종적으로 해당 URL로 리다이렉트
        return "redirect:" + url;
    }

    /**
     * 로그아웃 처리
     *
     * - 세션을 무효화하여 로그아웃 처리 후 메인 페이지로 리다이렉트합니다.
     * 
     * @param session 현재 사용자 세션
     * @return 메인 페이지로 리다이렉트
     * @throws Exception 예외 발생 시
     */
    @GetMapping("/logout")
    public String logout(HttpSession session) throws Exception {
        session.invalidate(); // 모든 세션 정보 삭제(로그아웃)
        return "redirect:/";
    }

    /**
     * 회원정보 수정 페이지로 이동
     *
     * - 세션에서 로그인한 회원의 아이디를 가져와 DB에서 회원정보를 조회합니다.
     * - 조회한 회원 정보를 모델에 담아 수정 페이지에 전달합니다.
     *
     * @param session 현재 사용자 세션
     * @param model   뷰에 데이터 전달용 모델
     * @throws Exception 예외 발생 시
     */
    @GetMapping("/modify")
    public void modify(HttpSession session, Model model) throws Exception {
        log.info("modify 호출");

        // 세션에서 로그인 회원 정보 가져오기
        String mbsp_id = ((MemberVO) session.getAttribute("login_auth")).getMbsp_id();

        // 회원 정보 조회
        MemberVO memberVO = memberService.modify(mbsp_id);

        // 뷰에 전달
        model.addAttribute("memberVO", memberVO);
        // 반환 타입 void → /templates/member/modify.html 렌더링
    }

    /**
     * 회원정보 수정 저장 처리
     *
     * - 수정된 회원 정보를 DB에 저장합니다.
     * - 저장 후 메인 페이지로 리다이렉트합니다.
     *
     * @param vo 수정할 회원 정보
     * @return 메인 페이지로 리다이렉트
     * @throws Exception 예외 발생 시
     */
    @PostMapping("/modify")
    public String modify(MemberVO vo) throws Exception {
        memberService.modify_save(vo); // 회원 정보 저장
        return "redirect:/";
    }

    /**
     * 마이페이지 이동
     *
     * - /templates/member/mypage.html 렌더링
     *
     * @throws Exception 예외 발생 시
     */
    @GetMapping("/mypage")
    public void mypage() throws Exception {
        // 별도 로직 없이 뷰만 렌더링
    }

    /**
     * 비밀번호 변경 페이지 이동
     *
     * - /templates/member/pwchange.html 렌더링
     *
     * @throws Exception 예외 발생 시
     */
    @GetMapping("/pwchange")
    public void pwchange() throws Exception {
        // 별도 로직 없이 뷰만 렌더링
    }
    
    /**
     * 비밀번호 변경 요청을 처리하는 컨트롤러 메서드입니다.
     *
     * - 현재 비밀번호가 맞는지 확인 후, 새 비밀번호로 변경합니다.
     * - 변경 성공 시 메인 페이지로 리다이렉트, 실패 시 비밀번호 변경 페이지로 리다이렉트합니다.
     * - RedirectAttributes를 활용해 1회성 메시지를 전달합니다.
     * - (이메일 알림 기능은 주석 처리되어 있음)
     *
     * @param mbsp_password 현재 비밀번호(사용자가 입력)
     * @param new_pw 새 비밀번호(사용자가 입력)
     * @param session 로그인 세션 정보
     * @param rttr 리다이렉트 시 메시지 전달용
     * @return 리다이렉트 경로
     * @throws Exception 예외 발생 시
     */
    @PostMapping("/pwchange")
    public String pwchString(
    		@RequestParam("cur_pw") String mbsp_password,
    		String new_pw,
    		HttpSession session,
    		RedirectAttributes rttr
    		) throws Exception {
    	
    	/**
    	 * =========================
    	 * RedirectAttributes란?
    	 * =========================
    	 * 
    	 * Spring MVC에서 리다이렉트(redirect:) 시 컨트롤러에서 다른 컨트롤러로 데이터를 전달할 때 사용하는 객체입니다.
    	 * 일반적으로 Model이나 Request에 담은 데이터는 redirect 시 소멸되지만, 
    	 * RedirectAttributes를 사용하면 안전하게 데이터를 전달할 수 있습니다.
    	 *
    	 * 주요 메서드:
    	 * 1. addAttribute("key", value)
    	 *    - 데이터를 URL 쿼리 파라미터로 전달합니다.
    	 *    - 예: redirect:/target?key=value
    	 *    - 주소창에 값이 노출되므로, 노출해도 되는 정보에 사용합니다.
    	 *
    	 * 2. addFlashAttribute("key", value)
    	 *    - 데이터를 임시로 세션에 저장했다가, 리다이렉트된 다음 요청에서 Model로 꺼내 쓸 수 있습니다.
    	 *    - 한 번 사용 후 자동 삭제(1회성 메시지, 예: 성공/실패 알림)에 적합합니다.
    	 *    - 주소창에 노출되지 않아 보안에 유리합니다.
    	 *
    	 * 사용 예시:
    	 * @example
    	 * // 컨트롤러에서 사용
    	 * @PostMapping("/save")
    	 * public String save(RedirectAttributes rttr) {
    	 *     rttr.addFlashAttribute("msg", "저장 완료!");
    	 *     return "redirect:/list";
    	 * }
    	 * // → /list 컨트롤러에서 @ModelAttribute("msg") 또는 Model로 접근 가능
    	 *
    	 * 정리:
    	 * - addAttribute: URL 파라미터 전달(노출됨, 여러 번 사용 가능)
    	 * - addFlashAttribute: 임시 세션 저장(노출 안됨, 1회성, PRG 패턴에 적합)
    	 */
    	
        String url = "";
        String msg = "";

        // 세션에서 로그인 회원 정보 추출
        MemberVO loginMember = (MemberVO) session.getAttribute("login_auth");
        String db_mbsp_password = loginMember.getMbsp_password();
        String mbsp_id = loginMember.getMbsp_id();
        String mbsp_email = loginMember.getMbsp_email();

        // 입력한 현재 비밀번호가 DB의 암호화된 비밀번호와 일치하는지 확인
        if (passwordEncoder.matches(mbsp_password, db_mbsp_password)) {
            // 새 비밀번호 암호화
            String encode_new_pw = passwordEncoder.encode(new_pw);

            // 비밀번호 변경 서비스 호출
            memberService.pwchange(mbsp_id, encode_new_pw);

            url = "/";
            msg = "success";

            // 비밀번호 변경 알림 이메일 발송(주석 처리)
            // String type = "mail/pwchange";
            // EmailDTO dto = new EmailDTO();
            // dto.setReceiverMail(mbsp_email);
            // dto.setSubject("Ezen Mall 비밀번호 변경 알림입니다.");
            // emailService.sendMail(type, dto, new_pw);
        } else {
            // 현재 비밀번호가 일치하지 않을 때
            url = "/member/pwchange";
            msg = "fail";
        }

        // 1회성 메시지(성공/실패)를 리다이렉트 대상 컨트롤러로 전달
        rttr.addFlashAttribute("msg", msg);

        // 지정한 URL로 리다이렉트
        return "redirect:" + url;
    }
    
    /**
     * 비밀번호/아이디 찾기 페이지로 이동하는 요청을 처리합니다.
     *
     * GET /member/lostpass
     *
     * @throws Exception 예외 발생 시
     */
    @GetMapping("/lostpass")
    public void lostpass() throws Exception {
        // 별도 로직 없이, /templates/member/lostpass.html 뷰를 렌더링합니다.
    }
    
    /**
     * 아이디 찾기(메일 발송) 요청을 처리합니다.
     *
     * @param mbsp_name 회원 이름
     * @param mbsp_email 회원 이메일
     * @return "success" 또는 "fail" 문자열과 HTTP 상태 코드
     * @throws Exception 예외 발생 시
     * @author main
     * @since 2025.05.01
     */
    @GetMapping("/idsearch")
    public ResponseEntity<String> idsearch(String mbsp_name, String mbsp_email) throws Exception {

        String result;

        // 이름과 이메일로 아이디 조회
        String mbsp_id = memberService.idsearch(mbsp_name, mbsp_email);

        if (mbsp_id != null) {
            // 아이디가 존재하면 메일 발송 (아래 코드는 실제 메일 발송 로직, 현재는 주석 처리)
            /*
            String type = "mail/idsearch";
            EmailDTO dto = new EmailDTO();
            dto.setReceiverMail(mbsp_email); // 받는 사람 메일주소
            dto.setSubject("Ezen Mall 아이디 찾기 결과를 보냅니다.");
            emailService.sendMail(type, dto, mbsp_id);
            */
            result = "success";
        } else {
            result = "fail";
        }

        // HTTP 200 OK와 결과 문자열 반환
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    /**
     * 임시 비밀번호 발급(메일 발송) 요청을 처리합니다.
     *
     * @param mbsp_id 회원 아이디
     * @param mbsp_email 회원 이메일
     * @return "success" 또는 "fail" 문자열과 HTTP 상태 코드
     * @throws Exception 예외 발생 시
     * @author main
     * @since 2025.05.01
     */
    @GetMapping("/pwtemp")
    public ResponseEntity<String> pwtemp(String mbsp_id, String mbsp_email) throws Exception {

        String result;

        // 아이디와 이메일이 DB에 존재하는지 확인
        String d_u_email = memberService.pwtemp_confirm(mbsp_id, mbsp_email);

        if (d_u_email != null) {
            result = "success";

            // 임시 비밀번호 생성 및 메일 발송 (아래 코드는 실제 메일 발송 및 임시 비밀번호 생성/저장 로직, 현재는 주석 처리)
            /*
            // 임시 비밀번호 생성 (EmailServiceImpl의 메서드 사용)
            String imsi_pw = emailService.createAuthCode();

            // 임시 비밀번호를 암호화하여 DB에 저장
            memberService.pwchange(mbsp_id, passwordEncoder.encode(imsi_pw));

            // 임시 비밀번호 메일 발송
            String type = "mail/pwtemp";
            EmailDTO dto = new EmailDTO();
            dto.setReceiverMail(d_u_email); // 받는 사람 메일주소
            dto.setSubject("Ezen Mall 임시비밀번호를 보냅니다.");
            emailService.sendMail(type, dto, imsi_pw);
            */
        } else {
            result = "fail";
        }

        return new ResponseEntity<>(result, HttpStatus.OK);
    }
}
