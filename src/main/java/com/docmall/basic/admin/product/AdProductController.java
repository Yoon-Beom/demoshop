package com.docmall.basic.admin.product;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;

import com.docmall.basic.admin.category.AdCategoryService;
import com.docmall.basic.common.utils.FileUtils;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * 관리자 상품 등록/관리 컨트롤러
 *
 * - 상품 등록 폼 출력, 상품 등록 처리(파일 업로드 포함) 기능을 담당합니다.
 * - @Controller: 스프링 MVC 컨트롤러로 등록
 * - @RequestMapping("/admin/product/*"): "/admin/product"로 시작하는 요청 처리
 * - @Slf4j: 로그 출력을 위한 Lombok 어노테이션
 * - @RequiredArgsConstructor: final 필드 생성자 자동 생성(Lombok)
 *
 * @author main
 * @since 2025.05.01
 */
@Slf4j // Lombok: 로그 객체 자동 생성(log)
@Controller
@RequiredArgsConstructor // Lombok: final 필드 생성자 자동 생성(의존성 주입)
@RequestMapping("/admin/product/*")
public class AdProductController {

    private final AdProductService adProductService; // 상품 서비스
    private final AdCategoryService adCategoryService; // 카테고리 서비스
    private final FileUtils fileUtils; // 파일 업로드/썸네일 유틸리티

    // application.properties에 정의된 파일 업로드 경로 주입
    @Value("${com.docmall.upload.path}")
    private String uploadPath;

    // CKEditor 이미지 업로드 경로(추후 확장용)
    @Value("${com.docmall.upload.ckeditor.path}")
    private String uploadCKPath;

    /**
     * 상품 등록 폼 페이지 출력
     *
     * GET /admin/product/pro_insert
     * - 1차 카테고리 목록을 모델에 담아 폼에서 사용
     *
     * @param model 뷰에 데이터 전달
     * @throws Exception 예외 발생 시
     */
    @GetMapping("/pro_insert")
    public void pro_insert(Model model) throws Exception {
        // 1차 카테고리 목록 조회 후 모델에 추가
        model.addAttribute("cate_list", adCategoryService.getFirstCategoryList());
        // 반환 타입이 void이므로, 요청 경로와 동일한 뷰(/templates/admin/product/pro_insert.html) 렌더링
    }

    /**
     * 상품 등록 처리(POST)
     *
     * POST /admin/product/pro_insert
     * - 상품 정보와 이미지 파일을 받아서 저장
     * - 이미지 파일은 날짜별 폴더에 UUID+원본명으로 업로드, 썸네일 자동 생성
     * - 상품 정보에 업로드 폴더/파일명 세팅 후 DB에 저장
     * - 등록 후 상품 목록 페이지로 리다이렉트
     *
     * @param vo 상품 정보(ProductVO)
     * @param pro_img_upload 업로드된 이미지 파일(MultipartFile)
     * @return 상품 목록 페이지로 리다이렉트
     * @throws Exception 예외 발생 시
     */
    @PostMapping("/pro_insert")
    public String pro_insert(ProductVO vo, MultipartFile pro_img_upload) throws Exception {

        // 오늘 날짜 폴더명 생성 (예: 2025/05/01)
        String dateFolder = fileUtils.getDateFolder();

        // 파일 업로드 및 실제 저장 파일명 반환 (UUID_원본명)
        String saveFileName = fileUtils.uploadFile(uploadPath, dateFolder, pro_img_upload);

        // 상품 VO에 업로드 폴더/파일명 세팅
        vo.setPro_up_folder(dateFolder);
        vo.setPro_img(saveFileName);

        // 상품 정보 DB 저장
        adProductService.pro_insert(vo);

        // 등록 후 상품 목록 페이지로 이동
        return "redirect:/admin/product/pro_list";
    }
}
