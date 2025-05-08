package com.docmall.basic.member;

import org.apache.ibatis.annotations.Param;

/**
 * 회원 관련 데이터베이스 작업을 담당하는 MyBatis 매퍼 인터페이스입니다.
 *
 * - 이 인터페이스의 메서드는 resources/mapper/MemberMapper.xml의 SQL과 매핑됩니다.
 * - 실제 구현체는 MyBatis가 런타임에 자동으로 생성합니다.
 * - @Mapper 어노테이션을 붙이거나, @MapperScan(basePackages = ...) 설정을 통해 빈으로 등록됩니다.
 * - 회원 등록, 아이디 중복 체크, 로그인, 정보 수정, 비밀번호 변경, 아이디/비밀번호 찾기 등
 *   다양한 DB 작업 메서드를 선언합니다.
 *
 * 공식 문서: https://mybatis.org/mybatis-3/ko/
 *
 * @author main
 * @since 2025.05.01
 */
public interface MemberMapper {

    /**
     * 아이디 중복 체크
     *
     * @param mbsp_id 중복 여부를 확인할 회원 아이디
     * @return 이미 존재하면 아이디(String), 없으면 null
     */
    String idCheck(String mbsp_id);

    /**
     * 회원 가입(INSERT)
     *
     * @param vo 회원 정보(MemberVO)
     */
    void join(MemberVO vo);

    /**
     * 로그인 시 회원 정보 조회
     *
     * @param mbsp_id 회원 아이디
     * @return 회원 정보(MemberVO), 없으면 null
     */
    MemberVO login(String mbsp_id);

    /**
     * 회원 정보 수정 폼에 기존 정보 조회
     *
     * @param mbsp_id 회원 아이디
     * @return 회원 정보(MemberVO)
     */
    MemberVO modify(String mbsp_id);

    /**
     * 회원 정보 수정(UPDATE)
     *
     * @param vo 수정할 회원 정보
     */
    void modify_save(MemberVO vo);

    /**
     * 비밀번호 변경
     *
     * @param mbsp_id 회원 아이디
     * @param mbsp_password 새 비밀번호(암호화된 값)
     */
    void pwchange(@Param("mbsp_id") String mbsp_id, @Param("mbsp_password") String mbsp_password);

    /**
     * 아이디 찾기 (이름+이메일로)
     *
     * @param mbsp_name 회원 이름
     * @param mbsp_email 회원 이메일
     * @return 회원 아이디, 없으면 null
     */
    String idsearch(@Param("mbsp_name") String mbsp_name, @Param("mbsp_email") String mbsp_email);

    /**
     * 임시 비밀번호 발급 전, 아이디+이메일 일치 여부 확인
     *
     * @param mbsp_id 회원 아이디
     * @param mbsp_email 회원 이메일
     * @return 아이디, 없으면 null
     */
    String pwtemp_confirm(@Param("mbsp_id") String mbsp_id, @Param("mbsp_email") String mbsp_email);
}
