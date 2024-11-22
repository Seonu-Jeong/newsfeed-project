package org.sparta.newsfeed.board.repository;

import org.sparta.newsfeed.entity.Board;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BoardRepository extends JpaRepository<Board, Long> {
    default Board findBoardById(Long id){
        return findById(id).orElseThrow(() -> new IllegalArgumentException("Board not found with id: " + id));
    }
}
