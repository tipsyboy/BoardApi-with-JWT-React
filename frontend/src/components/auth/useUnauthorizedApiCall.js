import React, { useState, useCallback } from "react";
import axios from "axios";

const BASE_URL = "http://localhost:8080";
const unauthorizedApiCall = axios.create({
  baseURL: BASE_URL,
});

const useUnauthorizedApiCall = () => {
  const unAuthGet = async (url) => {
    const response = await unauthorizedApiCall.get(url);
    return response;
  };

  const unAuthPost = async (url, requestData) => {
    const response = await unauthorizedApiCall.post(url, requestData);
    return response;
  };

  return { unAuthGet, unAuthPost };
};

export default useUnauthorizedApiCall;
