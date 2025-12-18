package com.aivle.backend.board.repository;

import com.aivle.backend.board.domain.Board;
import com.aivle.backend.board.domain.BoardLike;
import com.aivle.backend.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface BoardLikeRepository extends JpaRepository<BoardLike, Long> {
    Optional<BoardLike> findByBoardAndUser(Board board, User user);
}
