package com.docmall.basic.common.utils;

/**
 * 페이징 + 검색 기능을 위한 SearchCriteria 클래스입니다.
 *
 * - Criteria(부모)의 페이징 기능(page, perPageNum)에
 *   검색 조건(검색 종류, 검색어)을 추가로 관리합니다.
 * - 게시판, 상품 목록 등에서 페이징 + 검색 기능이 필요할 때 사용합니다.
 *
 * @author main
 * @since 2025.05.01
 */
public class SearchCriteria extends Criteria {

    /** 검색 종류(예: 제목, 내용, 작성자 등) */
    private String searchType;

    /** 검색어 */
    private String keyword;

    // 기본 생성자(필요시 명시적으로 작성 가능)

    /** 검색 종류 반환 */
    public String getSearchType() {
        return searchType;
    }

    /** 검색 종류 설정 */
    public void setSearchType(String searchType) {
        this.searchType = searchType;
    }

    /** 검색어 반환 */
    public String getKeyword() {
        return keyword;
    }

    /** 검색어 설정 */
    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }

    /**
     * 객체 상태를 문자열로 반환(디버깅, 로그 등에서 유용)
     */
    @Override
    public String toString() {
        return "SearchCriteria [searchType=" + searchType
                + ", keyword=" + keyword
                + ", getPage()=" + getPage()
                + ", getPerPageNum()=" + getPerPageNum() + "]";
    }
}
