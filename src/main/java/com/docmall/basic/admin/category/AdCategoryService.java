package com.docmall.basic.admin.category;

import java.util.List;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

/**
 * 카테고리 관련 비즈니스 로직을 처리하는 서비스 클래스입니다.
 *
 * - AdCategoryMapper(매퍼 인터페이스)를 주입받아 DB 연동 작업을 수행합니다.
 * - 1차/2차 카테고리 목록 조회, 카테고리 코드로 정보 조회 등 핵심 로직을 구현합니다.
 * - @Service 어노테이션을 통해 스프링 빈으로 등록됩니다.
 * - @RequiredArgsConstructor는 final 필드에 대한 생성자를 자동으로 생성합니다.
 *
 * @author main
 * @since 2025.05.09
 */
@RequiredArgsConstructor // Lombok: final 필드에 대한 생성자 자동 생성
@Service // 해당 클래스가 서비스 계층(비즈니스 로직)임을 명시
public class AdCategoryService {

    // 카테고리 관련 DB 작업을 담당하는 MyBatis 매퍼
    private final AdCategoryMapper adCategoryMapper;

    /**
     * 1차 카테고리 목록 조회
     * (cate_prtcode가 null인 최상위 카테고리만 조회)
     *
     * @return 1차 카테고리 리스트
     */
    public List<CategoryVO> getFirstCategoryList() {
        return adCategoryMapper.getFirstCategoryList();
    }

    /**
     * 2차 카테고리 목록 조회
     * (특정 1차 카테고리 코드에 속한 2차 카테고리만 조회)
     *
     * @param cate_prt_code 1차 카테고리 코드(부모 코드)
     * @return 2차 카테고리 리스트
     */
    public List<CategoryVO> getSecondCategoryList(Integer cate_prt_code) {
        return adCategoryMapper.getSecondCategoryList(cate_prt_code);
    }

    /**
     * 카테고리 코드로 해당 카테고리 정보 조회
     * (2차 카테고리 또는 임의의 카테고리 코드로 정보 조회)
     *
     * @param secondCategory 카테고리 코드
     * @return 카테고리 정보(CategoryVO)
     */
    public CategoryVO getFirstCategoryBySecondCategory(int secondCategory) {
        return adCategoryMapper.getFirstCategoryBySecondCategory(secondCategory);
    }
}
