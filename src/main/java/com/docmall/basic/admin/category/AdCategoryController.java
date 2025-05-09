package com.docmall.basic.admin.category;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * 관리자 카테고리 관련 웹 요청을 처리하는 컨트롤러 클래스입니다.
 *
 * - "/admin/category/*" 경로로 들어오는 모든 요청을 처리합니다.
 * - @RequiredArgsConstructor: final 필드(AdCategoryService) 생성자 자동 생성(Lombok)
 * - @Slf4j: 로그 출력을 위한 Lombok 어노테이션
 * - RESTful 방식으로 2차 카테고리 목록을 제공합니다.
 * 
 * @author main
 * @since 2025.05.09
 */
@Slf4j // Lombok: 로그 객체 자동 생성(log)
@RequiredArgsConstructor // Lombok: final 필드 생성자 자동 생성(의존성 주입)
@RequestMapping("/admin/category/*") // "/admin/category"로 시작하는 요청을 이 컨트롤러에서 처리
@Controller // 스프링 MVC 컨트롤러로 등록
public class AdCategoryController {

    // 카테고리 서비스(비즈니스 로직 담당)
    private final AdCategoryService adCategoryService;

    /**
     * 2차 카테고리 목록 조회 (REST API)
     * GET /admin/category/secondcategory/{cate_prt_code}
     * 
     * @param cate_prt_code 1차 카테고리 코드(URL 경로 변수)
     * @return ResponseEntity<List<CategoryVO>> (2차 카테고리 목록 + HTTP 상태 코드)
     * @throws Exception 예외 발생 시
     */
    @GetMapping("/secondcategory/{cate_prt_code}")
    public ResponseEntity<List<CategoryVO>> getSecondCategoryList(
            @PathVariable("cate_prt_code") Integer cate_prt_code) throws Exception {

        log.info("1차 카테고리 코드: {}", cate_prt_code);

        // 서비스 계층을 통해 2차 카테고리 목록 조회
        List<CategoryVO> secondCategoryList = adCategoryService.getSecondCategoryList(cate_prt_code);

        // 조회 결과를 HTTP 200 상태 코드와 함께 ResponseEntity로 반환
        return new ResponseEntity<>(secondCategoryList, HttpStatus.OK);
    }
}
