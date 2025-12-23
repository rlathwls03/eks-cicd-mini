// src/api/axiosInstance.js
console.log("🔥 axiosInstance loaded");

import axios from "axios";

// 안전한 baseURL 결정: Vite env가 없으면 로컬 백엔드(개발용)를 기본값으로 사용
const resolvedBaseURL = import.meta.env.VITE_API_BASE_URL || 'http://localhost:8080';
console.log("🔥 API BASE URL =", resolvedBaseURL);

const instance = axios.create({
  baseURL: resolvedBaseURL,
});

// 기본 POST Content-Type 보장
instance.defaults.headers.post['Content-Type'] = 'application/json';

// 요청 인터셉터: Authorization 헤더 추가 및 요청 로그
instance.interceptors.request.use((config) => {
    console.log("[axios] request interceptor start");
    const token = localStorage.getItem("accessToken");
    if (token) {
      config.headers.Authorization = `Bearer ${token}`;
    }

    try {
      const url = `${config.baseURL || ''}${config.url}`;
      console.log("➡️ axios request:", (config.method || '').toUpperCase(), url, "data:", config.data, "headers:", config.headers);
    } catch (e) {
      console.error("[axios] request logging error", e);
    }

    return config;
});

// 응답 인터셉터: 응답/에러 로그
instance.interceptors.response.use(
  (response) => {
    try {
      console.log("⬅️ axios response:", response.status, response.config.url, response.data);
    } catch (e) {
      console.error("[axios] response logging error", e);
    }
    return response;
  },
  (error) => {
    try {
      const status = error?.response?.status;
      const url = error?.response?.config?.url || error?.config?.url;
      const data = error?.response?.data;
      console.error("⬅️ axios response error:", status, url, data, error);
    } catch (e) {
      console.error("[axios] response error logging failed", e);
    }
    return Promise.reject(error);
  }
);

export default instance;
