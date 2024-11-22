package org.sparta.newsfeed.domain.board.repository;

import org.sparta.newsfeed.global.entity.Board;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface BoardRepository extends JpaRepository<Board, Long> {

    default Board findBoardById(Long id){
        return findById(id).orElseThrow(() -> new IllegalArgumentException("Board not found with id: " + id));
    }

    @Query(value = "SELECT * FROM board WHERE " +
            "user_id = ?1 OR " +
            "user_id = ANY(SELECT requested_user_id FROM friend WHERE ?1 = posted_user_id) OR " +
            "user_id = ANY(SELECT posted_user_id FROM friend WHERE ?1 = requested_user_id) " +
            "ORDER BY ?2 " +
            "LIMIT ?3 OFFSET ?4"
    ,nativeQuery = true)
    List<Board> findFriendsBoardList(Long user_id, String criteria, int limit, int offset);
}
