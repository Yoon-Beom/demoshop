package com.docmall.basic.member;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * 로그인 요청 정보를 담는 DTO(Data Transfer Object) 클래스입니다.
 *
 * - 사용자가 로그인 폼에 입력한 아이디와 비밀번호 정보를 전달할 때 사용합니다.
 * - DTO는 계층 간(Controller ↔ Service 등) 데이터 전달에 특화된 객체입니다.
 * - Lombok의 @Getter, @Setter, @ToString 어노테이션을 사용하여
 *   getter/setter, toString() 메서드를 자동 생성합니다.
 *
 * @author main
 * @since 2025.05.01
 */
@Getter // Lombok: 모든 필드의 getter 메서드 자동 생성
@Setter // Lombok: 모든 필드의 setter 메서드 자동 생성
@ToString // Lombok: toString() 메서드 자동 생성
public class LoginDTO {

    // 회원 아이디 (로그인 폼에서 입력)
    private String mbsp_id;

    // 회원 비밀번호 (로그인 폼에서 입력)
    private String mbsp_password;
}
