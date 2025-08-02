// 로그인
async function login(email, password) {
    try {
        const response = await fetch("/api/member/login", {
            method: "POST",
            headers: {
                "Content-Type": "application/json"
            },
            body: JSON.stringify({
                email: email,
                password: password
            })
        });

        const data = await response.json();

        if (!response.ok) {
            throw new Error(data.message || "로그인 실패");
        }

        return { response, data }; // (code,data,message) 실제 데이터(JwtToken)
    } catch (error) {
        alert(error.message);
        return null;
    }
}

// 회원가입
async function signup(name, email, password) {
    try {
        const response = await fetch("/api/member/signup", {
            method: "POST",
            headers: {
                "Content-Type": "application/json"
            },
            body: JSON.stringify({ name, email, password })
        });

        const data = await response.json();

        if (!response.ok) {
            throw new Error(data.message || "회원 가입 실패");
        }

        console.log(data.message);
        window.location.href = "/web/login";
        return { response, data };
    } catch (error) {
        alert(error.message);
        return null;
    }
}