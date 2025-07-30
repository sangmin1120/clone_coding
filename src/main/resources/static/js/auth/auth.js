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
        .then(response => {
            if (!response.ok)   throw new Error("로그인 실패");
            return response.json();
        })
        .then(data => {
            console.log("로그인 성공:", data);
            // 토큰 저장 로직

            // 페이지 이동
            // window.location.href="/";
        })
        .catch(error => {
            alert(error.message);
        });
}