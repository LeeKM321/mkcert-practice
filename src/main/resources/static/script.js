// 페이지 로드 시 SSL 정보 확인
document.addEventListener('DOMContentLoaded', function() {
    checkSslInfo();
    updateConnectionStatus();
    checkSecurityHeaders();
});

// SSL 연결 정보 확인
async function checkSslInfo() {
    try {
        const response = await fetch('/api/ssl-info');
        const data = await response.json();
        
        document.getElementById('protocol').textContent = data.protocol;
        document.getElementById('port').textContent = data.serverPort;
        document.getElementById('secure').textContent = data.secure ? '보안 연결' : '비보안 연결';
        document.getElementById('requestUrl').textContent = data.requestUrl;
        
        // 연결 상태 표시 업데이트
        const indicator = document.querySelector('.indicator');
        const statusText = document.querySelector('.status-indicator .text');
        
        if (data.secure) {
            indicator.classList.add('secure');
            statusText.textContent = 'HTTPS 보안 연결됨';
        } else {
            indicator.classList.add('insecure');
            statusText.textContent = 'HTTP 연결됨';
        }
        
    } catch (error) {
        console.error('SSL 정보 확인 실패:', error);
        displayTestResult('SSL 정보를 가져올 수 없습니다: ' + error.message, 'error');
    }
}

// 연결 상태 업데이트
function updateConnectionStatus() {
    const isHttps = window.location.protocol === 'https:';
    const indicator = document.querySelector('.indicator');
    const statusText = document.querySelector('.status-indicator .text');
    
    if (isHttps) {
        indicator.classList.add('secure');
        statusText.textContent = 'HTTPS 보안 연결됨';
    } else {
        indicator.classList.add('insecure');
        statusText.textContent = 'HTTP 연결됨';
    }
}

// HTTP 연결 테스트
async function testHttpConnection() {
    const httpUrl = `http://${window.location.hostname}:8080/api/ssl-info`;
    
    try {
        displayTestResult('HTTP 연결 테스트 중...', 'warning');
        
        const response = await fetch(httpUrl);
        const data = await response.json();
        
        const result = `HTTP 연결 테스트 결과:
프로토콜: ${data.protocol}
포트: ${data.serverPort}
보안 연결: ${data.secure ? '예' : '아니오'}
요청 URL: ${data.requestUrl}`;
        
        displayTestResult(result, 'success');
    } catch (error) {
        displayTestResult(`HTTP 연결 실패: ${error.message}`, 'error');
    }
}

// HTTPS 연결 테스트
async function testHttpsConnection() {
    const httpsUrl = `https://${window.location.hostname}:8443/api/ssl-info`;
    
    try {
        displayTestResult('HTTPS 연결 테스트 중...', 'warning');
        
        const response = await fetch(httpsUrl);
        const data = await response.json();
        
        const result = `HTTPS 연결 테스트 결과:
프로토콜: ${data.protocol}
포트: ${data.serverPort}
보안 연결: ${data.secure ? '예' : '아니오'}
요청 URL: ${data.requestUrl}`;
        
        displayTestResult(result, 'success');
    } catch (error) {
        displayTestResult(`HTTPS 연결 실패: ${error.message}`, 'error');
    }
}

// 보안 헤더 확인
async function checkSecurityHeaders() {
    try {
        const response = await fetch('/api/security-headers');
        const data = await response.json();
        
        // 보안 헤더 상태 업데이트
        document.getElementById('hstsStatus').textContent = data.hstsEnabled ? '활성화됨' : '비활성화됨';
        document.getElementById('hstsStatus').className = data.hstsEnabled ? 'success' : 'error';
        
        document.getElementById('secureStatus').textContent = data.secureEnabled ? '활성화됨' : '비활성화됨';
        document.getElementById('secureStatus').className = data.secureEnabled ? 'success' : 'error';
        
        // 응답 헤더에서 추가 정보 확인
        const hasXssProtection = response.headers.get('X-XSS-Protection');
        const hasContentTypeOptions = response.headers.get('X-Content-Type-Options');
        
        document.getElementById('xssStatus').textContent = hasXssProtection ? '설정됨' : '설정 안됨';
        document.getElementById('xssStatus').className = hasXssProtection ? 'success' : 'warning';
        
        document.getElementById('contentTypeStatus').textContent = hasContentTypeOptions ? '설정됨' : '설정 안됨';
        document.getElementById('contentTypeStatus').className = hasContentTypeOptions ? 'success' : 'warning';
        
        // 테스트 결과에 상세 정보 표시
        if (data.securityHeaders) {
            let headerInfo = '보안 헤더 확인 결과:\n';
            for (const [key, value] of Object.entries(data.securityHeaders)) {
                headerInfo += `${key}: ${value}\n`;
            }
            displayTestResult(headerInfo, 'success');
        }
        
    } catch (error) {
        console.error('보안 헤더 확인 실패:', error);
        displayTestResult(`보안 헤더 확인 실패: ${error.message}`, 'error');
    }
}

// Health Check 테스트
async function checkHealthStatus() {
    try {
        displayTestResult('Health Check 수행 중...', 'warning');
        
        const response = await fetch('/api/health');
        const data = await response.json();
        
        const healthInfo = `Health Check 결과:
상태: ${data.status}
프로토콜: ${data.protocol}
포트: ${data.port}
보안 연결: ${data.secure ? '예' : '아니오'}
타임스탬프: ${new Date(data.timestamp).toLocaleString()}`;
        
        displayTestResult(healthInfo, 'success');
    } catch (error) {
        displayTestResult(`Health Check 실패: ${error.message}`, 'error');
    }
}

// 테스트 결과 표시
function displayTestResult(message, type) {
    const resultsDiv = document.getElementById('testResults');
    resultsDiv.innerHTML = `<pre class="${type}">${message}</pre>`;
    
    // 결과 영역으로 스크롤
    resultsDiv.scrollIntoView({ behavior: 'smooth', block: 'nearest' });
}

// 인증서 정보 표시 (브라우저 API 활용)
function displayCertificateInfo() {
    if (window.location.protocol === 'https:') {
        const certInfo = document.getElementById('certDetails');
        
        // 브라우저 보안 정보 추가
        const securityInfo = document.createElement('div');
        securityInfo.className = 'security-info';
        securityInfo.innerHTML = `
            <h4>브라우저 보안 정보</h4>
            <p><strong>현재 연결:</strong> ${window.location.protocol.toUpperCase()}</p>
            <p><strong>호스트:</strong> ${window.location.hostname}</p>
            <p><strong>포트:</strong> ${window.location.port || '기본 포트'}</p>
            <p><strong>상태:</strong> 보안 연결 활성화</p>
        `;
        
        certInfo.appendChild(securityInfo);
    }
}

// 자동 새로고침 기능 (선택적)
function enableAutoRefresh(intervalSeconds = 30) {
    setInterval(() => {
        checkSslInfo();
        checkSecurityHeaders();
    }, intervalSeconds * 1000);
}

// 키보드 단축키 추가
document.addEventListener('keydown', function(event) {
    if (event.ctrlKey) {
        switch(event.key) {
            case '1':
                event.preventDefault();
                testHttpConnection();
                break;
            case '2':
                event.preventDefault();
                testHttpsConnection();
                break;
            case '3':
                event.preventDefault();
                checkSecurityHeaders();
                break;
            case '4':
                event.preventDefault();
                checkHealthStatus();
                break;
        }
    }
});

// 페이지 로드 완료 후 추가 초기화
window.addEventListener('load', function() {
    displayCertificateInfo();
    
    // 툴팁 추가
    const buttons = document.querySelectorAll('button');
    buttons.forEach((button, index) => {
        button.title = `단축키: Ctrl + ${index + 1}`;
    });
    
    console.log('Spring Boot mkcert HTTPS 실습 페이지 로드 완료');
    console.log('단축키: Ctrl + 1~4로 각 테스트 실행 가능');
});
