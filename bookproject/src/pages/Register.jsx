// 2025-12-05 16:52 소진님 마지막 수정으로 복구

import { Box, TextField, Button, Typography, Paper } from "@mui/material";
import { useNavigate } from "react-router-dom";
import { useState } from "react";
import { signupApi } from "../api/authApi";

export default function Register() {
    const nav = useNavigate();

    const [form, setForm] = useState({
        email: "",
        pw: "",
        pwCheck: "",
        nickname: ""
    });

    const handleChange = (e) => {
        setForm({ ...form, [e.target.name]: e.target.value });
    };

    const register = async () => {
      // 입력 검증: trim 및 비밀번호 확인
      const email = form.email?.trim();
      const nickname = form.nickname?.trim();
      const password = form.pw ?? "";

      if (!email || !password || !form.pwCheck) {
        alert("이메일과 비밀번호를 모두 입력하세요");
        return;
      }

      if (password !== form.pwCheck) {
        alert("비밀번호 확인이 일치하지 않습니다.");
        return;
      }

      try {
        const res = await signupApi({
          email,
          password,
          nickname,
        });

        console.log("signup response:", res);
        alert("회원가입이 완료되었습니다!");
        nav("/login");
      } catch (err) {
        console.error("signup error:", err);
        alert(err.response?.data?.message || "회원가입 실패");
      }
    };

    return (
        <Box
            sx={{
                width:"100%", height:"100vh",
                display:"flex", justifyContent:"center", alignItems:"center",
                backgroundColor:"#f5f5f5"
            }}
        >
            <Paper
                elevation={3}
                sx={{
                    width:400, p:4,
                    display:"flex", flexDirection:"column",
                    alignItems:"center"
                }}
            >
                <Typography variant="h5" sx={{mb:3, fontWeight:"bold"}}>
                    회원가입
                </Typography>

                <TextField
                    fullWidth label="이메일" name="email"
                    sx={{ mb: 2 }} value={form.email} onChange={handleChange}
                />

                <TextField
                    fullWidth label="닉네임" name="nickname"
                    sx={{ mb: 2 }} value={form.nickname} onChange={handleChange}
                />

                <TextField
                    fullWidth label="비밀번호" name="pw" type="password"
                    sx={{ mb: 2 }} value={form.pw} onChange={handleChange}
                />

                <TextField
                    fullWidth label="비밀번호 확인" name="pwCheck" type="password"
                    sx={{ mb: 4 }} value={form.pwCheck} onChange={handleChange}
                />

                <Button
                    fullWidth variant="contained"
                    sx={{ py: 1.5, fontSize: 17, bgcolor:"#00b6b8" }}
                    onClick={register}
                >
                    가입완료
                </Button>

                <Button
                    fullWidth variant="text" sx={{ mt: 2, fontSize: 16 }}
                    onClick={() => nav("/login")}
                >
                    로그인으로 돌아가기 →
                </Button>

            </Paper>
        </Box>
    );
}
