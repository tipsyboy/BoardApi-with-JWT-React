import React, { useState } from "react";
import axios from "axios";

const useAuthorizedApiCall = () => {
  const BASE_URL = "http://localhost:8080";
  const TOKEN_TYPE = "Bearer";
  const [accessToken, setAccessToken] = useState(
    JSON.parse(localStorage.getItem("accessToken"))
  );
  const [refreshToken, setRefreshToken] = useState(
    JSON.parse(localStorage.getItem("refreshToken"))
  );

  const authorizedApiCall = axios.create({
    baseURL: BASE_URL,
    headers: {
      Authorization: `${TOKEN_TYPE} ${accessToken}`,
    },
  });

  const authPost = async (url, requestData) => {
    const response = await authorizedApiCall.post(url, requestData);
    return response;
  };

  const authPut = async (url, requestData) => {
    const response = await authorizedApiCall.put(url, requestData);
    return response;
  };

  const authDelete = async (url) => {
    const response = await authorizedApiCall.delete(url);
    return response;
  };

  return { authPost, authPut, authDelete };
};

export default useAuthorizedApiCall;
