package com.codeit.mkcert.dto;

import java.util.Map;

/**
 * 보안 헤더 정보를 담는 응답 DTO 클래스
 */
public class SecurityHeadersResponse {

    private boolean hstsEnabled;
    private boolean secureEnabled;
    private String hstsMaxAge;
    private boolean includeSubdomains;
    private Map<String, String> securityHeaders;

    public SecurityHeadersResponse() {}

    public SecurityHeadersResponse(boolean hstsEnabled, boolean secureEnabled, String hstsMaxAge,
                                   boolean includeSubdomains, Map<String, String> securityHeaders) {
        this.hstsEnabled = hstsEnabled;
        this.secureEnabled = secureEnabled;
        this.hstsMaxAge = hstsMaxAge;
        this.includeSubdomains = includeSubdomains;
        this.securityHeaders = securityHeaders;
    }

    /**
     * Builder 패턴을 위한 정적 메서드
     */
    public static Builder builder() {
        return new Builder();
    }

    // Getters and Setters
    public boolean isHstsEnabled() {
        return hstsEnabled;
    }

    public void setHstsEnabled(boolean hstsEnabled) {
        this.hstsEnabled = hstsEnabled;
    }

    public boolean isSecureEnabled() {
        return secureEnabled;
    }

    public void setSecureEnabled(boolean secureEnabled) {
        this.secureEnabled = secureEnabled;
    }

    public String getHstsMaxAge() {
        return hstsMaxAge;
    }

    public void setHstsMaxAge(String hstsMaxAge) {
        this.hstsMaxAge = hstsMaxAge;
    }

    public boolean isIncludeSubdomains() {
        return includeSubdomains;
    }

    public void setIncludeSubdomains(boolean includeSubdomains) {
        this.includeSubdomains = includeSubdomains;
    }

    public Map<String, String> getSecurityHeaders() {
        return securityHeaders;
    }

    public void setSecurityHeaders(Map<String, String> securityHeaders) {
        this.securityHeaders = securityHeaders;
    }

    /**
     * Builder 클래스
     */
    public static class Builder {
        private boolean hstsEnabled;
        private boolean secureEnabled;
        private String hstsMaxAge;
        private boolean includeSubdomains;
        private Map<String, String> securityHeaders;

        public Builder hstsEnabled(boolean hstsEnabled) {
            this.hstsEnabled = hstsEnabled;
            return this;
        }

        public Builder secureEnabled(boolean secureEnabled) {
            this.secureEnabled = secureEnabled;
            return this;
        }

        public Builder hstsMaxAge(String hstsMaxAge) {
            this.hstsMaxAge = hstsMaxAge;
            return this;
        }

        public Builder includeSubdomains(boolean includeSubdomains) {
            this.includeSubdomains = includeSubdomains;
            return this;
        }

        public Builder securityHeaders(Map<String, String> securityHeaders) {
            this.securityHeaders = securityHeaders;
            return this;
        }

        public SecurityHeadersResponse build() {
            return new SecurityHeadersResponse(hstsEnabled, secureEnabled, hstsMaxAge,
                    includeSubdomains, securityHeaders);
        }
    }
}

