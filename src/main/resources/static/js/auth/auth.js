// 로그인
function login(accountId, password) {
    return fetch("/api/member/login", {
        method: "POST",
        headers: {
            "Content-Type": "application/json"
        },
        body: JSON.stringify({
            accountId: accountId,
            password: password
        })
    })
        .then(async response => {
            const data = await response.json();  // JSON 파싱 먼저

            if (!response.ok) {
                // 서버 오류 메시지를 던짐
                throw new Error(data.message || "로그인 실패");
            }

            // 성공 시 처리
            console.log("로그인 성공:", data);
            window.location.href = "/";
        })
        .catch(error => {
            // error.message 에 서버의 메시지가 들어있음
            alert(error.message);
        });
}

// 회원가입
function signup(name, accountId, password) {
    return fetch("/api/member/signup", {
        method: "POST",
        headers: {
            "Content-Type": "application/json"
        },
        body: JSON.stringify({
            name: name,
            accountId: accountId,
            password: password
        })
    })
        .then(response => {
            if (!response.ok)   throw new Error("회원 가입 실패");
            return response.json();
        })
        .then(data => {
            console.log("회원가입 성공:", data);
            // 로직

            // 페이지 이동
            window.location.href="/login";
        })
        .catch(error => {
            alert(error.message);
        });
}