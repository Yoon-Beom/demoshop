package com.docmall.basic;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import lombok.RequiredArgsConstructor;

/**
 * 메인 페이지 요청을 처리하는 컨트롤러입니다.
 *
 * - "/" 경로로 GET 요청이 들어오면 "index" 뷰(템플릿)를 반환합니다.
 * - 스프링 MVC의 @Controller 어노테이션을 사용하여 웹 요청을 처리합니다.
 * - @GetMapping은 해당 메서드가 GET 방식의 HTTP 요청을 처리하도록 지정합니다.
 * - "index"는 src/main/resources/templates/index.html 파일을 의미합니다.
 *
 * @author main
 * @since 2025.05.01
 */
@RequiredArgsConstructor // Lombok: final 필드 또는 @NonNull 필드에 대해 생성자 자동 생성
@Controller // 해당 클래스가 스프링 MVC 컨트롤러임을 나타냄
public class MainController {

    /**
     * 루트 경로("/") GET 요청을 처리하여 index.html 뷰를 반환합니다.
     *
     * @return "index" (타임리프 등 템플릿 엔진에서 index.html로 매핑)
     */
    @GetMapping("/")
    public String main() {
        // "/"로 요청이 오면 templates/index.html을 렌더링하여 반환
        return "index";
    }
}
