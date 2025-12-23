import instance from "./axiosInstance";

export const fetchMyInfo = async () => {
  const res = await instance.get("/auth/me");
  return res.data;
};

export const loginApi = async (email, password) => {
  const res = await instance.post("/auth/login", {
    email,
    password,
  });
  return res.data;
};

export const signupApi = async ({ email, password, nickname }) => {
  const res = await instance.post("/auth/signup", {
    email,
    password,
    nickname,
  });
  return res.data;
};

export const updateMyInfo = async (payload) => {
  const res = await instance.put("/auth/me", payload);
  return res.data;
};