package org.sparta.newsfeed.domain.board.like.repository;

import org.sparta.newsfeed.global.entity.*;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;


public interface BoardLikeRepository extends JpaRepository<BoardLike, Long> {

    boolean existsByUserAndBoard(User user, Board board);

    Optional<BoardLike> findByUserAndBoard(User loginUser, Board board);


}
