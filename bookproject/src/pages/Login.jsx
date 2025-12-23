// 2025-12-05 16:52 소진님 마지막 수정으로 복구

import { Box, TextField, Button, Typography, Paper } from "@mui/material";
import { useState } from "react";
import { useNavigate } from "react-router-dom";
import { loginApi } from "../api/authApi";

export default function Login() {
    const nav = useNavigate();

    const [form, setForm] = useState({
        email: "",
        pw: "",
    });

    // 로딩 상태 추가
    const [loading, setLoading] = useState(false);

    const handleChange = (e) => {
        setForm({ ...form, [e.target.name]: e.target.value });
    };

    const login = async () => {
      // 간단한 입력 검증
      const email = form.email?.trim();
      const password = form.pw ?? "";

      if (!email || !password) {
        alert("이메일과 비밀번호를 입력하세요");
        return;
      }

      try {
        setLoading(true);
        console.debug("login request payload:", { email, password });
        const data = await loginApi(email, password);

        localStorage.setItem("accessToken", data.accessToken);
        localStorage.setItem("refreshToken", data.refreshToken);
        localStorage.setItem("nickname", data.nickname ?? "");

        nav("/main");
      } catch (err) {
        // 서버 에러 상세를 콘솔에 찍고 사용자에게 가능한 한 구체적인 메시지를 보여줌
        console.error("로그인 중 에러 발생:", err);

        const status = err?.response?.status;
        // 서버가 { message: '...' } 형태로 에러를 보낼 수 있으므로 우선적으로 사용
        const serverMessage = err?.response?.data?.message ?? err?.response?.data ?? err?.message;

        if (status) {
          alert(`로그인 실패 (${status}): ${serverMessage}`);
        } else {
          alert(`로그인 실패: ${serverMessage}`);
        }
      } finally {
        setLoading(false);
      }
    };

    return (
        <Box
            sx={{
                width: "100%",
                height: "100vh",
                display: "flex",
                justifyContent: "center",
                alignItems: "center",
                bgcolor: "#f8f8f8",
            }}
        >
            <Paper
                elevation={6}
                sx={{
                    p: 5,
                    width: "400px",
                    borderRadius: "14px",
                    textAlign: "center",
                }}
            >
                <Typography fontSize={28} fontWeight="bold" mb={1}>
                    📚 BOOK LOGIN
                </Typography>
                <Typography fontSize={14} mb={4} color="#666">
                    도서 관리 시스템에 로그인하세요
                </Typography>

                <TextField
                    fullWidth
                    label="이메일"
                    name="email"
                    variant="outlined"
                    sx={{ mb: 2 }}
                    value={form.email}
                    onChange={handleChange}
                />

                <TextField
                    fullWidth
                    label="비밀번호"
                    name="pw"
                    type="password"
                    variant="outlined"
                    sx={{ mb: 4 }}
                    value={form.pw}
                    onChange={handleChange}
                />

                <Button
                    fullWidth
                    variant="contained"
                    sx={{ py: 1.5, fontSize: 18, bgcolor: "#00b6b8" }}
                    onClick={login}
                    disabled={loading}
                >
                    {loading ? "로딩..." : "로그인"}
                </Button>

                <Button
                    fullWidth
                    variant="text"
                    sx={{ mt: 2, fontSize: 16, color: "#333" }}
                    onClick={() => nav("/register")}
                >
                    회원가입 →
                </Button>
            </Paper>
        </Box>
    );
}
