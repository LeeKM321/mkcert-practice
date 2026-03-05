package com.codeit.mkcert.controller;

import com.codeit.mkcert.dto.SecurityHeadersResponse;
import com.codeit.mkcert.dto.SslInfoResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

/**
 * SSL 정보 및 보안 헤더를 제공하는 REST API 컨트롤러
 */
@RestController
@RequestMapping("/api")
public class SslInfoController {

    /**
     * 현재 SSL 연결 정보를 반환하는 API
     *
     * @param request HTTP 요청 객체
     * @return SSL 연결 정보
     */
    @GetMapping("/ssl-info")
    public ResponseEntity<SslInfoResponse> getSslInfo(HttpServletRequest request) {

        // 요청 URL 구성
        String requestUrl = request.getScheme() + "://" +
                request.getServerName() + ":" +
                request.getServerPort() +
                request.getRequestURI();

        SslInfoResponse response = SslInfoResponse.builder()
                .protocol(request.getScheme().toUpperCase())
                .serverPort(request.getServerPort())
                .secure(request.isSecure())
                .scheme(request.getScheme())
                .requestUrl(requestUrl)
                .build();

        return ResponseEntity.ok(response);
    }

    /**
     * 보안 헤더 정보를 확인하고 반환하는 API
     *
     * @param request HTTP 요청 객체
     * @param response HTTP 응답 객체
     * @return 보안 헤더 정보
     */
    @GetMapping("/security-headers")
    public ResponseEntity<SecurityHeadersResponse> getSecurityHeaders(
            HttpServletRequest request,
            HttpServletResponse response) {

        // HSTS 헤더 설정
        String hstsHeader = "max-age=31536000; includeSubDomains";
        response.setHeader("Strict-Transport-Security", hstsHeader); // HSTS 설정(강제 HTTPS 요청)

        // 추가 보안 헤더 설정
        response.setHeader("X-Content-Type-Options", "nosniff"); // 내가 지정한 Content-Type 이외의 파일을 브라우저가 추측해서 실행하지 말아라
        response.setHeader("X-Frame-Options", "DENY"); // 우리 페이지가 타 페이지의 iframe 형식으로 제공되는 것을 거부한다.
        response.setHeader("X-XSS-Protection", "1; mode=block"); // 사용자가 게시판 등에 악성 자바스크립트를 심으려고 하는 것이 감지되면 (XSS 공격) 화면을 차단시켜라

        // 보안 헤더 정보 수집
        Map<String, String> securityHeaders = new HashMap<>();
        securityHeaders.put("Strict-Transport-Security", hstsHeader);
        securityHeaders.put("X-Content-Type-Options", "nosniff");
        securityHeaders.put("X-Frame-Options", "DENY");
        securityHeaders.put("X-XSS-Protection", "1; mode=block");

        SecurityHeadersResponse securityResponse = SecurityHeadersResponse.builder()
                .hstsEnabled(true)
                .secureEnabled(request.isSecure())
                .hstsMaxAge("31536000")
                .includeSubdomains(true)
                .securityHeaders(securityHeaders)
                .build();

        return ResponseEntity.ok(securityResponse);
    }

    /**
     * 간단한 Health Check API
     *
     * @return 상태 정보
     */
    @GetMapping("/health")
    public ResponseEntity<Map<String, Object>> health(HttpServletRequest request) {
        Map<String, Object> health = new HashMap<>();
        health.put("status", "UP");
        health.put("protocol", request.getScheme().toUpperCase());
        health.put("port", request.getServerPort());
        health.put("secure", request.isSecure());
        health.put("timestamp", System.currentTimeMillis());

        return ResponseEntity.ok(health);
    }


}
