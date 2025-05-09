package com.docmall.basic.admin.category;

import java.util.List;

/**
 * 카테고리 관련 데이터베이스 작업을 담당하는 MyBatis 매퍼 인터페이스입니다.
 *
 * - 이 인터페이스의 메서드는 resources/mapper/AdCategoryMapper.xml의 SQL과 매핑됩니다.
 * - 실제 구현체는 MyBatis가 런타임에 자동으로 생성합니다.
 * - 1차/2차 카테고리 목록 조회, 카테고리 코드로 정보 조회 등 DB 작업 메서드를 선언합니다.
 *
 * @author main
 * @since 2025.05.09
 */
public interface AdCategoryMapper {

    /**
     * 1차 카테고리 목록 조회
     * (cate_prtcode가 null인 최상위 카테고리만 조회)
     *
     * @return 1차 카테고리 리스트
     */
    List<CategoryVO> getFirstCategoryList();

    /**
     * 2차 카테고리 목록 조회
     * (특정 1차 카테고리 코드에 속한 2차 카테고리만 조회)
     *
     * @param cate_prt_code 1차 카테고리 코드(부모 코드)
     * @return 2차 카테고리 리스트
     */
    List<CategoryVO> getSecondCategoryList(Integer cate_prt_code);

    /**
     * 카테고리 코드로 해당 카테고리 정보 조회
     * (2차 카테고리 또는 임의의 카테고리 코드로 정보 조회)
     *
     * @param secondCategory 카테고리 코드
     * @return 카테고리 정보(CategoryVO)
     */
    CategoryVO getFirstCategoryBySecondCategory(int secondCategory);
}
