import React, { useState } from "react";
import axios from "axios";

const useAuthorizedApiCall = () => {
  const TOKEN_TYPE = "Bearer";
  const [accessToken, setAccessToken] = useState(
    JSON.parse(localStorage.getItem("accessToken"))
  );
  const [refreshToken, setRefreshToken] = useState(
    JSON.parse(localStorage.getItem("refreshToken"))
  );

  const authorizedApiCall = axios.create({
    baseURL: "http://localhost:8080",
    headers: {
      Authorization: `${TOKEN_TYPE} ${accessToken}`,
    },
  });

  const authPost = async (url, requestData) => {
    const response = await authorizedApiCall.post(url, requestData);
    return response;
  };

  return { authPost };
};

export default useAuthorizedApiCall;
