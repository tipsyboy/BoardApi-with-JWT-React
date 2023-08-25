import React, { useState, useCallback } from "react";
import axios from "axios";

const unauthorizedApiCall = axios.create({
  baseURL: "http://localhost:8080",
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
