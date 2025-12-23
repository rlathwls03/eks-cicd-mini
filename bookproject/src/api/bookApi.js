// 2025-12-05 16:34 형택님 마지막 수정으로 복구

import instance from "./axiosInstance";

// 이제 공유된 instance를 사용합니다 (baseURL + Authorization 인터셉터 포함)

// 도서 등록
export const createBook = async (userId, data) => {
    const response = await instance.post(`/api/books?userId=${userId}`, data);
    return response.data;
};

// 도서 목록 불러오기
export const fetchBooks = async () => {
    const response = await instance.get("/api/books");
    return response.data.content; // Page 객체의 content 배열만 사용
};

// 도서 상세 조회
export const fetchBookDetail = async (bookId) => {
    const response = await instance.get(`/api/books/${bookId}`);
    return response.data;
};

// 도서 수정
export const updateBook = async (bookId, data) => {
    const response = await instance.put(`/api/books/${bookId}`, data);
    return response.data;
};

// 도서 삭제
export const deleteBook = async (bookId) => {
    const response = await instance.delete(`/api/books/${bookId}`);
    return response.data;
};

// 책 좋아요
export const likeBook = async (bookId) => {
    const response = await instance.post(`/api/books/${bookId}/like?liked=true`);
    return response.data;
};

// 책 싫어요
export const dislikeBook = async (bookId) => {
    const response = await instance.post(`/api/books/${bookId}/like?liked=false`);
    return response.data;
};
