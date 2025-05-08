package com.docmall.basic.admin.product;

import java.util.HashMap;
import java.util.List;

import com.docmall.basic.common.utils.SearchCriteria;

/**
 * 상품 관련 데이터베이스 작업을 담당하는 MyBatis 매퍼 인터페이스입니다.
 *
 * - 이 인터페이스의 메서드는 resources/mapper/AdProductMapper.xml의 SQL과 매핑됩니다.
 * - 실제 구현체는 MyBatis가 런타임에 자동으로 생성합니다.
 * - 상품 등록, 목록 조회(검색/페이징), 수정, 삭제 등 다양한 DB 작업 메서드를 선언합니다.
 *
 * @author main
 * @since 2025.05.01
 */
public interface AdProductMapper {

    /**
     * 상품 등록(INSERT)
     *
     * @param vo 등록할 상품 정보(ProductVO)
     */
    void pro_insert(ProductVO vo);

    /**
     * 상품 목록 조회(검색/페이징)
     *
     * @param cri 검색 및 페이징 조건(SearchCriteria)
     * @return 상품 목록(List<ProductVO>)
     */
    List<ProductVO> pro_list(SearchCriteria cri);

    /**
     * 전체 상품 개수 조회(검색 포함)
     *
     * @param cri 검색 조건(SearchCriteria)
     * @return 전체 상품 개수
     */
    int getTotalCount(SearchCriteria cri);

    /**
     * 선택 상품 일괄 삭제(상품 번호 배열)
     *
     * @param pro_num_arr 삭제할 상품 번호 배열
     */
    void pro_sel_delete_2(int[] pro_num_arr);

    /**
     * 선택 상품 일괄 삭제(이름 + 번호 배열)
     *
     * @param map pro_name(상품명), pro_num_arr(상품 번호 배열) 포함
     */
    void pro_sel_delete_3(HashMap<String, Object> map);

    /**
     * 상품 상세 조회(수정 폼용)
     *
     * @param pro_num 상품 번호
     * @return 상품 상세 정보(ProductVO)
     */
    ProductVO pro_edit_form(Integer pro_num);

    /**
     * 상품 정보 수정(UPDATE)
     *
     * @param vo 수정할 상품 정보(ProductVO)
     */
    void pro_edit_ok(ProductVO vo);

    /**
     * 상품 단건 삭제(번호로)
     *
     * @param pro_num 삭제할 상품 번호
     */
    void pro_delete(Integer pro_num);

}
