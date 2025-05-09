package com.docmall.basic.admin.category;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * 카테고리 정보를 담는 VO(Value Object) 클래스입니다.
 *
 * - 상품 카테고리의 코드, 부모 카테고리 코드, 카테고리명을 관리합니다.
 * - DB의 카테고리 테이블과 1:1로 매핑되어 사용됩니다.
 * - Lombok의 @Getter, @Setter, @ToString 어노테이션을 사용하여
 *   getter/setter, toString() 메서드를 자동 생성합니다.
 *   (Lombok 공식 문서: https://projectlombok.org/)
 *
 * @author main
 * @since 2025.05.01
 */
@Getter // Lombok: 모든 필드의 getter 메서드 자동 생성
@Setter // Lombok: 모든 필드의 setter 메서드 자동 생성
@ToString // Lombok: toString() 메서드 자동 생성
public class CategoryVO {

    /** 카테고리 코드 (PK) */
    private Integer cate_code;

    /** 부모 카테고리 코드 (1차/2차 구분 등 계층 구조 구현에 사용) */
    private Integer cate_prtcode;

    /** 카테고리명 */
    private String cate_name;
}
