package com.docmall.basic.common.utils;

/**
 * 페이징 기능을 위한 Criteria 클래스입니다.
 *
 * - 사용자가 선택한 페이지 번호와 한 페이지당 게시물 개수를 관리합니다.
 * - MyBatis 매퍼에서 페이징 쿼리의 파라미터로 사용됩니다.
 * - getPageStart()는 LIMIT 절의 시작 인덱스를 계산합니다.
 * - getter/setter 메서드는 MyBatis에서 반드시 필요합니다.
 *
 * @author main
 * @since 2025.05.01
 */
public class Criteria {

    /** 사용자가 선택한 페이지 번호 (1부터 시작) */
    private int page;

    /** 한 페이지에 출력할 게시물 개수 */
    private int perPageNum;

    /**
     * 기본 생성자
     * - page: 1
     * - perPageNum: 10
     */
    public Criteria() {
        this.page = 1;
        this.perPageNum = 10;
    }

    /** 현재 페이지 번호 반환 */
    public int getPage() {
        return page;
    }

    /** 페이지 번호 설정 (1 이상만 허용) */
    public void setPage(int page) {
        if(page <= 0) {
            this.page = 1;
            return;
        }
        this.page = page;
    }

    /** 한 페이지당 게시물 개수 설정 (1~100 사이만 허용) */
    public void setPerPageNum(int perPageNum) {
        if(perPageNum <= 0 || perPageNum > 100) {
            this.perPageNum = 10;
            return;
        }
        this.perPageNum = perPageNum;
    }

    /**
     * LIMIT 절의 시작 인덱스 반환
     * - (현재 페이지 - 1) * 페이지당 게시물 수
     * - 예: page=2, perPageNum=10 → 10
     * - MyBatis 매퍼에서 #{pageStart}로 참조
     */
    public int getPageStart() {
        return (this.page - 1) * perPageNum;
    }

    /** 한 페이지당 게시물 개수 반환 (MyBatis 매퍼에서 #{perPageNum}로 참조) */
    public int getPerPageNum() {
        return perPageNum;
    }

    @Override
    public String toString() {
        return "Criteria [page=" + page + ", perPageNum=" + perPageNum + "]";
    }
}
