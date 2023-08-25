import { useState, useEffect } from "react";
import jwtDecode from "jwt-decode"; // JWT 디코딩 라이브러리

const useCurrentUser = () => {
  const [currentUser, setCurrentUser] = useState(null);

  useEffect(() => {
    // JWT 토큰을 로컬 스토리지나 세션 스토리지에서 가져옵니다.
    const accessToken = localStorage.getItem("accessToken"); // 적절한 토큰 저장 위치 사용

    if (accessToken) {
      try {
        // JWT 디코딩하여 사용자 정보 추출
        const decodedToken = jwtDecode(accessToken);
        setCurrentUser(decodedToken);
      } catch (error) {
        console.error("Error decoding JWT token:", error);
        // 토큰 디코딩 에러 처리
      }
    }
  }, []);

  return currentUser;
};

export default useCurrentUser;
