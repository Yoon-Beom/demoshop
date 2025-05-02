package com.docmall.basic.member;

/**
 * 회원 관련 데이터베이스 작업을 담당하는 MyBatis 매퍼 인터페이스입니다.
 *
 * - 이 인터페이스의 메서드는 resources/mapper/MemberMapper.xml의 SQL과 매핑됩니다.
 * - 실제 구현체는 MyBatis가 런타임에 자동으로 생성합니다.
 * - @Mapper 어노테이션을 붙이거나, @MapperScan(basePackages = ...) 설정을 통해 빈으로 등록됩니다.
 * - 회원 등록, 아이디 중복 체크 등 DB 작업 메서드를 선언합니다.
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

    // TODO: 회원 정보 조회, 수정, 삭제 등 추가 메서드를 필요에 따라 선언하세요.
}
