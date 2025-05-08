package com.docmall.basic.member;

import org.springframework.stereotype.Service;
import lombok.RequiredArgsConstructor;

/**
 * 회원 관련 비즈니스 로직을 처리하는 서비스 클래스입니다.
 *
 * - MemberMapper(매퍼 인터페이스)를 주입받아 DB 연동 작업을 수행합니다.
 * - 주로 회원 가입, 로그인, 아이디 중복 체크 등 핵심 로직을 구현합니다.
 * - @Service 어노테이션을 통해 스프링 빈으로 등록됩니다.
 * - @RequiredArgsConstructor는 final 필드에 대한 생성자를 자동으로 생성합니다.
 *   (Lombok 공식 문서: https://projectlombok.org/features/RequiredArgsConstructor)
 *
 * @author main
 * @since 2025.05.01
 */
@RequiredArgsConstructor // Lombok: final 필드에 대한 생성자 자동 생성
@Service // 해당 클래스가 서비스 계층(비즈니스 로직)임을 명시
public class MemberService {

    // 회원 관련 DB 작업을 담당하는 MyBatis 매퍼
    private final MemberMapper memberMapper;

    /**
     * 아이디 중복 체크
     *
     * @param mbsp_id 중복 여부를 확인할 회원 아이디
     * @return 이미 존재하면 아이디(String), 없으면 null
     */
    public String idCheck(String mbsp_id) {
        // MemberMapper의 idCheck 메서드를 호출하여 아이디 존재 여부 확인
        return memberMapper.idCheck(mbsp_id);
    }

    /**
     * 회원 가입(INSERT)
     *
     * @param vo 회원 정보(MemberVO)
     */
    public void join(MemberVO vo) {
        memberMapper.join(vo);
    }

    /**
     * 회원 로그인: 아이디로 회원 정보를 조회합니다.
     *
     * @param mbsp_id 로그인할 회원의 아이디
     * @return 해당 아이디의 회원 정보(MemberVO), 없으면 null
     */
    public MemberVO login(String mbsp_id) {
        return memberMapper.login(mbsp_id);
    }

    /**
     * 회원정보 수정 화면에 출력할 회원 정보를 조회합니다.
     *
     * @param mbsp_id 회원 아이디
     * @return 회원 정보(MemberVO)
     */
    public MemberVO modify(String mbsp_id) {
        return memberMapper.modify(mbsp_id);
    }

    /**
     * 회원정보 수정 내용을 저장합니다.
     *
     * @param vo 수정할 회원 정보(MemberVO)
     */
    public void modify_save(MemberVO vo) {
        memberMapper.modify_save(vo);
    }

    /**
     * 비밀번호를 변경합니다.
     *
     * @param mbsp_id       회원 아이디
     * @param mbsp_password 변경할(암호화된) 비밀번호
     */
    public void pwchange(String mbsp_id, String mbsp_password) {
        memberMapper.pwchange(mbsp_id, mbsp_password);
    }

    /**
     * 이름과 이메일로 아이디를 찾습니다.
     *
     * @param mbsp_name  회원 이름
     * @param mbsp_email 회원 이메일
     * @return 찾은 아이디(String), 없으면 null
     */
    public String idsearch(String mbsp_name, String mbsp_email) {
        return memberMapper.idsearch(mbsp_name, mbsp_email);
    }

    /**
     * 임시 비밀번호 발급을 위해 아이디와 이메일이 일치하는지 확인합니다.
     *
     * @param mbsp_id    회원 아이디
     * @param mbsp_email 회원 이메일
     * @return 일치하면 "success", 불일치하면 "fail" 등
     */
    public String pwtemp_confirm(String mbsp_id, String mbsp_email) {
        return memberMapper.pwtemp_confirm(mbsp_id, mbsp_email);
    }

}
