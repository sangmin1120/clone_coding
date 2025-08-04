// 정보 반환 api
async function info() {
    try {
        const accessToken = localStorage.getItem("accessToken");

        const response = await fetch("/api/member/info", {

            method: "GET",
            headers: {
                "Content-Type": "application/json",
                "Authorization": accessToken
            },
            withCredential: true
        });

        const data = await response.json();

        if (!response.ok) {
            throw new Error(data.message || "정보 반환 실패");
        }

        return { response, data }; // (code,data,message),(name,email) json 자체 파싱
    } catch (error) {
        alert(error.message);
        return null;
    }
}