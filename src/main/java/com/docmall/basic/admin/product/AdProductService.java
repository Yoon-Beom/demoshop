package com.docmall.basic.admin.product;

import java.util.HashMap;
import java.util.List;

import org.springframework.stereotype.Service;

import com.docmall.basic.common.utils.SearchCriteria;

import lombok.RequiredArgsConstructor;

/**
 * 상품 관련 비즈니스 로직을 처리하는 서비스 클래스입니다.
 *
 * - AdProductMapper(매퍼 인터페이스)를 주입받아 DB 연동 작업을 수행합니다.
 * - 상품 등록, 목록 조회(검색/페이징), 수정, 삭제 등 핵심 로직을 구현합니다.
 * - @Service 어노테이션을 통해 스프링 빈으로 등록됩니다.
 * - @RequiredArgsConstructor는 final 필드에 대한 생성자를 자동으로 생성합니다.
 *
 * @author main
 * @since 2025.05.01
 */
@RequiredArgsConstructor // Lombok: final 필드에 대한 생성자 자동 생성
@Service // 해당 클래스가 서비스 계층(비즈니스 로직)임을 명시
public class AdProductService {

    // 상품 관련 DB 작업을 담당하는 MyBatis 매퍼
    private final AdProductMapper adProductMapper;

    /**
     * 상품 등록(INSERT)
     *
     * @param vo 등록할 상품 정보(ProductVO)
     */
    public void pro_insert(ProductVO vo) {
        adProductMapper.pro_insert(vo);
    }

    /**
     * 상품 목록 조회(검색/페이징)
     *
     * @param cri 검색 및 페이징 조건(SearchCriteria)
     * @return 상품 목록(List<ProductVO>)
     */
    public List<ProductVO> pro_list(SearchCriteria cri) {
        return adProductMapper.pro_list(cri);
    }

    /**
     * 전체 상품 개수 조회(검색 포함)
     *
     * @param cri 검색 조건(SearchCriteria)
     * @return 전체 상품 개수
     */
    public int getTotalCount(SearchCriteria cri) {
        return adProductMapper.getTotalCount(cri);
    }

    /**
     * 선택 상품 일괄 삭제(상품 번호 배열)
     *
     * @param pro_num_arr 삭제할 상품 번호 배열
     */
    public void pro_sel_delete_2(int[] pro_num_arr) {
        adProductMapper.pro_sel_delete_2(pro_num_arr);
    }

    /**
     * 선택 상품 일괄 삭제(이름 + 번호 배열)
     *
     * @param check 삭제할 상품 번호 배열
     * @param pro_name 상품명
     */
    public void pro_sel_delete_3(int[] check, String pro_name) {
        // 두 파라미터를 Map으로 묶어서 매퍼에 전달
        HashMap<String, Object> map = new HashMap<>();
        map.put("pro_num_arr", check);
        map.put("pro_name", pro_name);

        adProductMapper.pro_sel_delete_3(map);
    }

    /**
     * 상품 상세 조회(수정 폼용)
     *
     * @param pro_num 상품 번호
     * @return 상품 상세 정보(ProductVO)
     */
    public ProductVO pro_edit_form(Integer pro_num) {
        return adProductMapper.pro_edit_form(pro_num);
    }

    /**
     * 상품 정보 수정(UPDATE)
     *
     * @param vo 수정할 상품 정보(ProductVO)
     */
    public void pro_edit_ok(ProductVO vo) {
        adProductMapper.pro_edit_ok(vo);
    }

    /**
     * 상품 단건 삭제(번호로)
     *
     * @param pro_num 삭제할 상품 번호
     */
    public void pro_delete(Integer pro_num) {
        adProductMapper.pro_delete(pro_num);
    }

    // TODO: 필요에 따라 추가적인 상품 관련 비즈니스 로직을 구현하세요.
}
