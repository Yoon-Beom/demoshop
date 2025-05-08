package com.docmall.basic.admin.product;

import java.util.Date;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * 상품 정보를 담는 VO(Value Object) 클래스입니다.
 *
 * - 상품의 주요 속성(번호, 카테고리, 이름, 가격, 할인율, 출판사, 설명 등)과
 *   이미지 파일 정보, 재고, 구매여부, 리뷰 수, 등록/수정일 등을 포함합니다.
 * - DB의 상품 테이블과 1:1로 매핑되어 사용됩니다.
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
public class ProductVO {

    /** 상품 고유 번호 (PK) */
    private Integer pro_num;

    /** 2차 카테고리 코드 (외래키) */
    private Integer cate_code;

    /** 상품명 */
    private String pro_name;

    /** 상품 가격 */
    private int pro_price;

    /** 할인율(%) */
    private int pro_discount;

    /** 출판사(또는 제조사 등) */
    private String pro_publisher;

    /** 상품 상세 설명 */
    private String pro_content;

    /** 이미지 업로드 날짜 폴더명 (예: 2025/05/08) */
    private String pro_up_folder;

    /** 상품 이미지 파일명 (서버에 저장된 실제 파일명, 유일값) */
    private String pro_img;

    /** 재고 수량 */
    private int pro_amount;

    /** 구매 가능 여부 (예: 'Y', 'N') */
    private String pro_buy;

    /** 상품 리뷰 수 */
    private int pro_review;

    /** 상품 등록일 */
    private Date pro_date;

    /** 상품 정보 수정일 */
    private Date pro_updatedate;
}
