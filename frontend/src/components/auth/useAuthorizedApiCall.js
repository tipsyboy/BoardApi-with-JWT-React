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

  const isUnAuthorizedError = (error) => {
    return error.config && error.response && error.response.status === 401;
  };

  const shouldRetry = (config) => {
    return config.retries.count < 3;
  };

  const authApiInterceptorsException = async (error) => {
    error.config.retries = error.config.retries || { count: 0 };

    if (isUnAuthorizedError(error) && shouldRetry(error.config)) {
      try {
        const response = await reissueToken();

        // update loginData
        localStorage.setItem(
          "accessToken",
          JSON.stringify(response.data.accessToken)
        );
        localStorage.setItem(
          "refreshToken",
          JSON.stringify(response.data.refreshToken)
        );
        setAccessToken(response.data.accessToken);
        setRefreshToken(response.data.refreshToken);

        error.config.retries.count += 1;
        authorizedApiCall.defaults.headers.common.Authorization = `${TOKEN_TYPE} ${response.data.accessToken}`; // update the accessToken
        error.config.headers.Authorization = `Bearer ${response.data.accessToken}`;

        return authorizedApiCall.request(error.config);
      } catch (reissueError) {
        return Promise.reject(reissueError);
      }
    }
  };

  authorizedApiCall.interceptors.response.use(
    null,
    authApiInterceptorsException
  );

  const reissueToken = async () => {
    const requestData = {
      accessToken: accessToken,
      refreshToken: refreshToken,
    };
    try {
      const response = await authorizedApiCall.post(
        `/api/auth/reissue`,
        requestData
      );
      return response;
    } catch (error) {
      return Promise.reject(error);
    }
  };

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
