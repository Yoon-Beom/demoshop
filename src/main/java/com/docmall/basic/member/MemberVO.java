package com.docmall.basic.member;

import java.util.Date;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * 회원 정보를 담는 VO(Value Object) 클래스입니다.
 *
 * - 데이터베이스의 회원 테이블 컬럼과 1:1로 매핑되는 필드들로 구성되어 있습니다.
 * - Lombok의 @Getter, @Setter, @ToString 어노테이션을 사용하여
 *   getter/setter, toString() 메서드를 자동 생성합니다.
 * - VO(Value Object)는 데이터 전달 및 조회에 사용되는 객체입니다.
 *
 * @author main
 * @since 2025.05.01
 */
@Getter // Lombok: 모든 필드의 getter 메서드 자동 생성
@Setter // Lombok: 모든 필드의 setter 메서드 자동 생성
@ToString // Lombok: toString() 메서드 자동 생성
public class MemberVO {

    // 회원 아이디
    private String mbsp_id;

    // 회원 이름
    private String mbsp_name;

    // 회원 이메일
    private String mbsp_email;

    // 회원 비밀번호(암호화 저장 권장)
    private String mbsp_password;

    // 우편번호
    private String mbsp_zipcode;

    // 기본 주소
    private String mbsp_addr;

    // 상세 주소
    private String mbsp_deaddr;

    // 전화번호
    private String mbsp_phone;

    // 닉네임
    private String mbsp_nick;

    // 이메일/문자 수신 동의 여부
    private String mbsp_receive;

    // 포인트
    private int mbsp_point;

    // 마지막 로그인 일시
    private Date mbsp_lastlogin;

    // 가입일
    private Date mbsp_datesub;

    // 정보 수정일
    private Date mbsp_updatedate;
}
