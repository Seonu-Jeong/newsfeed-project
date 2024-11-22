package org.sparta.newsfeed.domain.board.repository;

import org.sparta.newsfeed.global.entity.Board;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BoardRepository extends JpaRepository<Board, Long> {
    default Board findBoardById(Long id){
        return findById(id).orElseThrow(() -> new IllegalArgumentException("Board not found with id: " + id));
    }
}
