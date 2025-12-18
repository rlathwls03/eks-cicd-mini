package com.aivle.backend.board.service;

import com.aivle.backend.board.domain.Board;
import com.aivle.backend.board.domain.BoardLike;
import com.aivle.backend.board.dto.BoardRequestDto;
import com.aivle.backend.board.repository.BoardLikeRepository;
import com.aivle.backend.board.repository.BoardRepository;
import com.aivle.backend.user.entity.User;
import com.aivle.backend.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class BoardService {
    private final BoardRepository boardRepository;
    private final UserRepository userRepository;
    private final BoardLikeRepository boardLikeRepository;

    // 생성
    public Board insertBoard(String userId, BoardRequestDto boardRequestDto) {
        User user = userRepository.findByEmail(userId)
                .orElseThrow(() -> new RuntimeException("가입되지 않은 이메일입니다."));

        Board board = Board.builder()
                .user(user)
                .title(boardRequestDto.getTitle())
                .content(boardRequestDto.getContent())
                .build();
        return boardRepository.save(board);
    }

    // 업데이트 (PUT) - 전체 수정
    public Board updateBoard(Long boardId, BoardRequestDto boardRequestDto) {
        Board board = getBoard(boardId);

        board.setTitle(boardRequestDto.getTitle());
        board.setContent(boardRequestDto.getContent());

        return boardRepository.save(board);
    }

    // 삭제
    public void deleteBoard(Long boardId) {
        Board b = getBoard(boardId);
        boardRepository.delete(b);
    }

    // 게시판 정보 조회
    private Board getBoard(Long boardId) {
        // orElseThrow(): id에 해당하는 데이터가 없다면 예외 발생 처리
        return boardRepository.findById(boardId).orElseThrow(() -> new IllegalArgumentException("게시판을 찾을 수 없습니다."));
    }

    // 게시판 정보 조회 - 사용자 요청
    @Transactional
    public Board findBoard(Long boardId){
        // 조회 수 증가
        boardRepository.updateViews(boardId);
        return getBoard(boardId);
    }

    // 게시판 목록 조회
    public Page<Board> findBoardAll(Pageable pageable) {
        return boardRepository.findAll(pageable);
    }

    // 좋아요 싫어요 토글
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public Board toggleLike(Long boardId, Long userId, boolean liked) {
        Board board = boardRepository.findById(boardId)
                .orElseThrow(() -> new RuntimeException("게시글을 찾을 수 없습니다."));

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("사용자를 찾을 수 없습니다."));

        Optional<BoardLike> existing = boardLikeRepository.findByBoardAndUser(board, user);

        if (existing.isPresent()) {
            BoardLike boardLike = existing.get();

            // 같은 반응이면 취소
            if (boardLike.isLiked() == liked) {
                boardLikeRepository.delete(boardLike);
                if (liked) board.setLikes(board.getLikes() - 1);
                else board.setDislikes(board.getDislikes() - 1);
            } else {
                // 반대 반응으로 변경
                boardLike.setLiked(liked);
                if (liked) {
                    board.setLikes(board.getLikes() + 1);
                    board.setDislikes(board.getDislikes() - 1);
                } else {
                    board.setDislikes(board.getDislikes() + 1);
                    board.setLikes(board.getLikes() - 1);
                }
            }
        } else {
            // 처음 누름
            BoardLike newLike = BoardLike.builder()
                    .board(board)
                    .user(user)
                    .liked(liked)
                    .build();
            boardLikeRepository.save(newLike);

            if (liked) board.setLikes(board.getLikes() + 1);
            else board.setDislikes(board.getDislikes() + 1);
        }

        return boardRepository.save(board);
    }

}
