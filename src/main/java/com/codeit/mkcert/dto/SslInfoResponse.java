package com.codeit.mkcert.dto;


/**
 * SSL 연결 정보를 담는 응답 DTO 클래스
 */
public class SslInfoResponse {

    private String protocol;
    private int serverPort;
    private boolean secure;
    private String scheme;
    private String requestUrl;

    public SslInfoResponse() {}

    public SslInfoResponse(String protocol, int serverPort, boolean secure, String scheme, String requestUrl) {
        this.protocol = protocol;
        this.serverPort = serverPort;
        this.secure = secure;
        this.scheme = scheme;
        this.requestUrl = requestUrl;
    }

    /**
     * Builder 패턴을 위한 정적 메서드
     */
    public static Builder builder() {
        return new Builder();
    }

    // Getters and Setters
    public String getProtocol() {
        return protocol;
    }

    public void setProtocol(String protocol) {
        this.protocol = protocol;
    }

    public int getServerPort() {
        return serverPort;
    }

    public void setServerPort(int serverPort) {
        this.serverPort = serverPort;
    }

    public boolean isSecure() {
        return secure;
    }

    public void setSecure(boolean secure) {
        this.secure = secure;
    }

    public String getScheme() {
        return scheme;
    }

    public void setScheme(String scheme) {
        this.scheme = scheme;
    }

    public String getRequestUrl() {
        return requestUrl;
    }

    public void setRequestUrl(String requestUrl) {
        this.requestUrl = requestUrl;
    }

    /**
     * Builder 클래스
     */
    public static class Builder {
        private String protocol;
        private int serverPort;
        private boolean secure;
        private String scheme;
        private String requestUrl;

        public Builder protocol(String protocol) {
            this.protocol = protocol;
            return this;
        }

        public Builder serverPort(int serverPort) {
            this.serverPort = serverPort;
            return this;
        }

        public Builder secure(boolean secure) {
            this.secure = secure;
            return this;
        }

        public Builder scheme(String scheme) {
            this.scheme = scheme;
            return this;
        }

        public Builder requestUrl(String requestUrl) {
            this.requestUrl = requestUrl;
            return this;
        }

        public SslInfoResponse build() {
            return new SslInfoResponse(protocol, serverPort, secure, scheme, requestUrl);
        }
    }
}
